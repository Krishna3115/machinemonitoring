import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./Machinedetails.css";
import API_BASE_URL from "../../apiConfig";

export default function MachineDetails() {
  const { serialNo } = useParams();
  const [machine, setMachine] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get(`${API_BASE_URL}/api/machines-production/${serialNo}`)
      .then((res) => setMachine(res.data))
      .catch(() => setError("Unable to load machine details"))
      .finally(() => setLoading(false));
  }, [serialNo]);

  if (loading) return <div className="status">Loading machine details…</div>;
  if (error) return <div className="status error">{error}</div>;

  const formatDate = (date) => {
    if (!date) return "—";
    return new Date(date).toLocaleString();
  };

  return (
    <div className="machine-page">
      <div className="machine-card">
        <header className="machine-header">
          <h1>Machine Details</h1>
          <span className="serial">{machine.machineSerialNo}</span>
        </header>

        <div className="machine-grid">
          <Info label="Job Card No" value={machine.jobCardNo} />
          <Info label="Motor No" value={machine.motorNo} />
          <Info label="Sensor No" value={machine.sensorNo} />
          <Info label="Applicator No" value={machine.applicatorNo} />
          <Info label="Battery No" value={machine.batteryNo} />
          <Info label="Solar Controller No" value={machine.solarChargeControllerNo} />
          <Info label="Solar Panel No 1" value={machine.solarPanelNo1} />
          <Info label="Solar Panel No 2" value={machine.solarPanelNo2} />
          <Info label="Cabinet No" value={machine.cabinetNo} />
          <Info label="Batch Counter No" value={machine.batchCounterNo} />
          <Info label="MCB No" value={machine.mcbNo} />
          <Info label="Gear Pump No" value={machine.gearPumpNo} />
          <Info label="Division" value={machine.division} />
          <Info label="Section" value={machine.section} />
          <Info label="Dispatch Date" value={formatDate(machine.dispatchDate)} />
          <Info label="Delivered Date" value={formatDate(machine.deliveredDate)} />
          <Info label="Installation Date" value={formatDate(machine.installationDate)} />
          <Info label="Last Maintenance" value={formatDate(machine.maintenanceEnded)} />
        </div>

        <footer className="machine-footer">
          <p>© TBL Machine Monitoring System</p>
        </footer>
      </div>
    </div>
  );
}

function Info({ label, value }) {
  return (
    <div className="info-box">
      <span className="info-label">{label}</span>
      <span className="info-value">{value || "—"}</span>
    </div>
  );
}
