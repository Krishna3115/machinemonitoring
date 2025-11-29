import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useLocation } from "react-router-dom";
import "./CompleteMaintenanceForm.css";
import API_BASE_URL from "../../apiConfig";
import CaptureImage from "../../components/CaptureImage";

export default function CompleteMaintenanceForm() {
  const navigate = useNavigate();
  const location = useLocation();

  // Get machine info passed from previous page
  const machineId = location.state?.machineId;
  const modelNo = location.state?.modelNo;
  const inspectionId = location.state?.inspectionId;
  const technicianId = Number(localStorage.getItem("userId"));

  // Redirect if essential data is missing
  useEffect(() => {
    if (!machineId || !modelNo || !technicianId) {
      alert("Required data missing. Redirecting...");
      navigate("/technician/start-maintenance");
    }
  }, [machineId, modelNo, technicianId, navigate]);

  // Form state
  const [form, setForm] = useState({
    id: inspectionId || null,
    machineId: machineId || null,
    modelNo: modelNo || "",
    dateOfInspection: new Date().toISOString().split("T")[0],
    greaseLevel: "",
    batteryVoltage: "",
    solarPanelVoltage: "",
    wheelCount: "",
    cycleTime: "",
    solarChargeController: "",
    batchCounter: "",
    doorLock: "",
    sensorCondition: "",
    applicatorStatus: "",
    motorPumpStatus: "",
    greaseLevelPhotoUrl: "",
    machineInfoPlatePhotoUrl: "",
    applicatorPhotoUrl: "",
    remark: "",
    machineStatus: "",
    maintenanceDate: new Date().toISOString(),
    maintenanceStarted: new Date().toISOString(),
    maintenanceEnded: new Date().toISOString(),
    inspectedByUserId: technicianId,
    technicianUserId: technicianId,
  });

  // Handle input changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    const numberFields = ["batteryVoltage", "solarPanelVoltage", "wheelCount", "cycleTime"];
    setForm((prev) => ({
      ...prev,
      [name]: numberFields.includes(name) ? (value === "" ? "" : Number(value)) : value,
    }));
  };

  // Submit form
  const handleSubmit = async () => {
  if (!form.modelNo || !form.inspectedByUserId || !form.id) {
    alert("Missing essential data. Cannot submit form.");
    return;
  }

  try {
    const payload = new FormData();

    // Append the form data as JSON string
    const formCopy = { ...form };
    delete formCopy.greaseLevelPhotoUrl;
    delete formCopy.machineInfoPlatePhotoUrl;
    delete formCopy.applicatorPhotoUrl;

    payload.append("form", JSON.stringify(formCopy));

    // Append images as files if they exist
    if (form.greaseLevelPhotoUrl) {
      payload.append("greaseLevelPhoto", form.greaseLevelPhotoUrl); // should be a File/Blob
    }
    if (form.machineInfoPlatePhotoUrl) {
      payload.append("machineInfoPlatePhoto", form.machineInfoPlatePhotoUrl); // File/Blob
    }
    if (form.applicatorPhotoUrl) {
      payload.append("applicatorPhoto", form.applicatorPhotoUrl); // File/Blob
    }

    const response = await axios.post(
      `${API_BASE_URL}/api/maintenance/submit`,
      payload,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    );

    if (response.status === 200) {
      alert("Maintenance submitted successfully.");
      navigate("/technician/start-maintenance");
    }
  } catch (err) {
    console.error(err);
    alert("Failed to submit maintenance. Check console for details.");
  }
};


  return (
    <div className="form-container">
      <button onClick={() => navigate("/technician")}>← Back</button>
      <h2>📋 Complete Maintenance - {modelNo}</h2>

      <div className="form-grid">
        <div className="form-group">
          <label>Date of Inspection</label>
          <input
            type="date"
            name="dateOfInspection"
            value={form.dateOfInspection}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Grease Level</label>
          <input type="text" name="greaseLevel" value={form.greaseLevel} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Battery Voltage</label>
          <input type="number" name="batteryVoltage" value={form.batteryVoltage} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Solar Panel Voltage</label>
          <input type="number" name="solarPanelVoltage" value={form.solarPanelVoltage} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Wheel Count</label>
          <input type="number" name="wheelCount" value={form.wheelCount} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Cycle Time</label>
          <input type="number" name="cycleTime" value={form.cycleTime} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Solar Charge Controller</label>
          <input type="text" name="solarChargeController" value={form.solarChargeController} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Batch Counter</label>
          <input type="text" name="batchCounter" value={form.batchCounter} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Door Lock</label>
          <input type="text" name="doorLock" value={form.doorLock} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Sensor Condition</label>
          <input type="text" name="sensorCondition" value={form.sensorCondition} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Applicator Status</label>
          <input type="text" name="applicatorStatus" value={form.applicatorStatus} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Motor Pump Status</label>
          <input type="text" name="motorPumpStatus" value={form.motorPumpStatus} onChange={handleChange} />
        </div>

        {/* Capture images */}
        <CaptureImage
          label="Grease Level Photo"
          onUpload={(url) => setForm((p) => ({ ...p, greaseLevelPhotoUrl: url }))}
        />
        <CaptureImage
          label="Machine Info Plate Photo"
          onUpload={(url) => setForm((p) => ({ ...p, machineInfoPlatePhotoUrl: url }))}
        />
        <CaptureImage
          label="Applicator Photo"
          onUpload={(url) => setForm((p) => ({ ...p, applicatorPhotoUrl: url }))}
        />

        <div className="form-group">
          <label>Machine Status</label>
          <select name="machineStatus" value={form.machineStatus} onChange={handleChange}>
            <option value="">-- Select Status --</option>
            <option value="working properly">Working Properly</option>
            <option value="not working">Not Working</option>
          </select>
        </div>

        <div className="form-group">
          <label>Remark</label>
          <textarea name="remark" value={form.remark} onChange={handleChange} />
        </div>
      </div>

      <button onClick={handleSubmit}>✅ Submit Maintenance</button>
    </div>
  );
}
