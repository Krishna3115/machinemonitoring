package com.tblmonitoring.tblmonitor.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tblmonitoring.tblmonitor.dto.GreaseCalculationResult;
import com.tblmonitoring.tblmonitor.entity.GreaseFillRecord;
import com.tblmonitoring.tblmonitor.entity.GreaseFrequency;
import com.tblmonitoring.tblmonitor.entity.InstallationRecord;
import com.tblmonitoring.tblmonitor.repository.GreaseFrequencyRepository;
import com.tblmonitoring.tblmonitor.repository.GreaseRefillRepository;
import com.tblmonitoring.tblmonitor.repository.InstallationRecordRepository;

@Service
public class GreaseCalculationServiceImpl implements GreaseCalculationService {

    @Autowired
    private InstallationRecordRepository installRepo;

    @Autowired
    private GreaseFrequencyRepository freqRepo;

    @Autowired
    private GreaseRefillRepository refillRepo;

    @Override
    public GreaseCalculationResult calculateGreaseEstimate(String modelNo, double initialGreaseLeftKg) {
        InstallationRecord install = installRepo.findByModelNo(modelNo)
            .orElseThrow(() -> new RuntimeException("Installation record not found for model: " + modelNo));

        GreaseFrequency freq = freqRepo.findByModelNo(modelNo)
            .orElseThrow(() -> new RuntimeException("Grease frequency not found for model: " + modelNo));

        List<GreaseFillRecord> refills = refillRepo.findByModelNoOrderByFillDateAsc(modelNo);

        if (install.getInstallationEnded() == null) {
            throw new RuntimeException("Installation ended date is null for model: " + modelNo);
        }

        LocalDate lastDate = install.getInstallationEnded().toLocalDate();
        int wheelCount = install.getWheelCount();
        double timePerCycle = install.getTimeCount(); // seconds
        double dailyWheels = freq.getWheelsPerDay();
        double rateGPerSec = freq.getGreaseReleaseRateGmPerSec();

        double cyclesPerDay = dailyWheels / wheelCount;
        double greasePerCycleG = timePerCycle * rateGPerSec;
        double dailyUseG = cyclesPerDay * greasePerCycleG;

        double availableG = initialGreaseLeftKg * 1000;

        // Process each refill
        for (GreaseFillRecord r : refills) {
            LocalDate d = r.getFillDate();
            if (d.isBefore(lastDate)) continue;

            long daysPassed = ChronoUnit.DAYS.between(lastDate, d);
            availableG = Math.max(0, availableG - daysPassed * dailyUseG);

            if (Boolean.TRUE.equals(r.getIsFullTank())) {
                availableG = 50_000; // full 50 kg
            } else {
                availableG = r.getRemainingGreaseKg() * 1000;
            }

            lastDate = d;
        }

        // Simulate to today
        LocalDate today = LocalDate.now();
        LocalDate tenKgDate = null, emptyDate = null;
        double tempG = availableG;
        LocalDate dateCursor = lastDate;

        while (!dateCursor.isAfter(today) && tempG > 0) {
            if (tempG <= 10_000 && tenKgDate == null) {
                tenKgDate = dateCursor;
            }
            tempG -= dailyUseG;
            if (tempG <= 0) {
                tempG = 0;
                emptyDate = dateCursor;
                break;
            }
            dateCursor = dateCursor.plusDays(1);
        }

        double remainingKg = tempG / 1000;
        int daysUntilEmpty = (int) Math.floor(tempG / dailyUseG);
        int tenKgDays = (int) Math.floor(10_000 / dailyUseG);
        LocalDate emptyEst = (emptyDate != null ? emptyDate : today.plusDays(daysUntilEmpty));

        GreaseCalculationResult result = new GreaseCalculationResult();
        result.setModelNo(modelNo);
        result.setGreaseUsedPerDayKg(dailyUseG / 1000);
        result.setDaysUntilEmpty(daysUntilEmpty);
        result.setEstimatedEmptyDate(emptyEst);
        result.setTenKgSurvivalDays(tenKgDays);
        result.setGreaseRemainingKg(remainingKg);
        result.setTenKgReachedDate(tenKgDate);
        result.setEmptyDate(emptyDate);

        return result;
    }

    @Override
    public List<GreaseCalculationResult> getMachinesWithLowGrease() {
        List<InstallationRecord> installs = installRepo.findAll();
        List<GreaseFrequency> freqs = freqRepo.findAll();
        List<GreaseCalculationResult> low = new ArrayList<>();

        for (InstallationRecord inst : installs) {
            String model = inst.getModelNo();
            Optional<GreaseFrequency> fo = freqs.stream()
                .filter(f -> f.getModelNo().equalsIgnoreCase(model))
                .findFirst();

            if (fo.isEmpty()) continue;
            GreaseFrequency f = fo.get();

            Integer wc = inst.getWheelCount();
            Integer tc = inst.getTimeCount();
            Double gl = inst.getGreaseLevelKg();

            if (wc == null || tc == null || gl == null || wc == 0) continue;

            double cyclesDaily = (double) f.getWheelsPerDay() / wc;
            double greasePerCycle = f.getGreaseReleaseRateGmPerSec() * tc;
            double usePerDayKg = cyclesDaily * greasePerCycle / 1000.0;

            int daysToEmpty = (int) Math.floor(gl / usePerDayKg);
            int daysToTen = (int) Math.floor(10 / usePerDayKg);

            if (daysToEmpty <= 10) {
                GreaseCalculationResult r = new GreaseCalculationResult();
                r.setModelNo(model);
                r.setGreaseUsedPerDayKg(usePerDayKg);
                r.setDaysUntilEmpty(daysToEmpty);
                r.setEstimatedEmptyDate(LocalDate.now().plusDays(daysToEmpty));
                r.setGreaseRemainingKg(gl);
                r.setTenKgSurvivalDays(daysToTen);
                low.add(r);
            }
        }

        System.out.println("Total machines with low grease: " + low.size());
        return low;
    }
}
