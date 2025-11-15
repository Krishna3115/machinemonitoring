import React, { useState } from "react";
import axios from "axios";
import { useNavigate, useLocation } from "react-router-dom";
import "./CompleteMaintenanceForm.css";
import API_BASE_URL from "../../apiConfig";

export default function CompleteMaintenanceForm() {
  const navigate = useNavigate();
  const location = useLocation();
  const modelNo = location.state?.modelNo || "";

  const technicianId = Number(localStorage.getItem("userId"));

  const [form, setForm] = useState({
    modelNo,
    greaseLevel: "",
    greaseLevelPhotoUrl: "",
    batteryReading: "",
    solarPanelReading1: "",
    solarPanelReading2: "",
    timeCount: "",
    wheelCount: "",
    machineInfoPlatePhotoUrl: "",
    sensor: "",
    applicator: "",
    machineStatus: "",
    remark: "",
    maintenanceDate: new Date().toISOString(),
    maintenanceStarted: new Date().toISOString(),
    maintenanceEnded: new Date().toISOString(),
    inspectedByUserId: technicianId,
    technicianUserId: technicianId,
  });

  const machineStatusOptions = [
    { value: "working properly", label: "Working Properly" },
    
  ];

  const handleChange = (e) => {
    const { name, value } = e.target;
    const numericFields = ["wheelCount", "timeCount"];
    setForm((prev) => ({
      ...prev,
      [name]: numericFields.includes(name) ? (value === "" ? "" : parseInt(value)) : value,
    }));
  };

  const handleUpload = async (e, field) => {
    const file = e.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);
    try {
      const res = await axios.post(`${API_BASE_URL}/api/upload/image`, formData);
      setForm((prev) => ({ ...prev, [field]: `${API_BASE_URL}${res.data.url}` }));
    } catch {
      alert("Image upload failed.");
    }
  };

  const handleSubmit = async () => {
    try {
      await axios.post(`${API_BASE_URL}/api/inspections/create`, form);
      alert("Maintenance submitted successfully.");
      navigate("/technician/start-maintenance");
    } catch (err) {
      console.error(err);
      alert("Failed to submit maintenance.");
    }
  };

  return (
    <div className="form-container">
      <button className="back-button" onClick={() => navigate("/technician")}>
        ← Back
      </button>
      <h2>📋 Complete Maintenance - {modelNo}</h2>

      <div className="form-grid">
        <div className="form-group">
          <label>Grease Level</label>
          <input type="text" name="greaseLevel" value={form.greaseLevel} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Battery Reading</label>
          <input type="text" name="batteryReading" value={form.batteryReading} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Solar Panel Reading 1</label>
          <input type="text" name="solarPanelReading1" value={form.solarPanelReading1} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Solar Panel Reading 2</label>
          <input type="text" name="solarPanelReading2" value={form.solarPanelReading2} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Time Count</label>
          <input type="number" name="timeCount" value={form.timeCount} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Wheel Count</label>
          <input type="number" name="wheelCount" value={form.wheelCount} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Sensor</label>
          <input type="text" name="sensor" value={form.sensor} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Applicator</label>
          <input type="text" name="applicator" value={form.applicator} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Machine Status</label>
          <select name="machineStatus" value={form.machineStatus} onChange={handleChange} required>
            <option value="">-- Select Status --</option>
            {machineStatusOptions.map((option) => (
              <option key={option.value} value={option.value}>
                {option.label}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>Remark</label>
          <textarea name="remark" value={form.remark} onChange={handleChange} />
        </div>

        <div className="form-group">
          <label>Grease Level Photo</label>
          <input type="file" accept="image/*" onChange={(e) => handleUpload(e, "greaseLevelPhotoUrl")} />
          {form.greaseLevelPhotoUrl && <img src={form.greaseLevelPhotoUrl} className="preview-image" alt="Grease" />}
        </div>

        <div className="form-group">
          <label>Machine Info Plate Photo</label>
          <input type="file" accept="image/*" onChange={(e) => handleUpload(e, "machineInfoPlatePhotoUrl")} />
          {form.machineInfoPlatePhotoUrl && (
            <img src={form.machineInfoPlatePhotoUrl} className="preview-image" alt="Plate" />
          )}
        </div>
      </div>

      <button className="submit-button" onClick={handleSubmit}>
        ✅ Submit Maintenance
      </button>
    </div>
  );
}
