import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./StartMaintenance.css";
import API_BASE_URL from "../../apiConfig";

export default function StartMaintenance() {
  const navigate = useNavigate();
  const [machineId, setMachineId] = useState("");
  const [machineData, setMachineData] = useState(null);
  const [maintenanceStarted, setMaintenanceStarted] = useState(false);
  const [formVisible, setFormVisible] = useState(false);
  const [remarks, setRemarks] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const technicianId = localStorage.getItem("userId");

  const handleFetchMachine = async () => {
    if (!machineId) return;

    setIsSubmitting(true);
    setErrorMessage("");

    try {
      const response = await axios.get(`${API_BASE_URL}/api/machines/location/${machineId}`);
      setMachineData(response.data);
    } catch (err) {
      setErrorMessage("Error fetching machine data.");
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleStartMaintenance = async () => {
  if (!machineId) {
    setErrorMessage("Machine ID is required.");
    return;
  }

  setIsSubmitting(true);
  setErrorMessage("");

  try {
    await axios.post(
      `${API_BASE_URL}/api/machines/start-maintenance`,
      null,
      {
        params: {
          modelNo: machineId,
          technicianId: technicianId // Replace with dynamic ID as needed
        }
      }
    );

    setMaintenanceStarted(true);
  } catch (err) {
    setErrorMessage("Failed to start maintenance.");
  } finally {
    setIsSubmitting(false);
  }
};

  // ✅ Move this outside to be in scope
  const handleNavigateToComplete = () => {
    navigate("/technician/complete-maintenance", {
      state: { modelNo: machineId }
    });
  };

  const handleSubmitMaintenance = async () => {
    if (!remarks || !machineId) {
      setErrorMessage("Please fill all fields.");
      return;
    }

    setIsSubmitting(true);
    setErrorMessage("");

    try {
      await axios.post(`${API_BASE_URL}/api/inspections/create`, {
        machineId: Number(machineId),
        remarks: remarks,
        section: machineData?.section,
        division: machineData?.division,
        poleNo: machineData?.poleNo,
        fromKm: machineData?.fromKm,
        toKm: machineData?.toKm,
      });

      alert("Maintenance submitted successfully!");
      navigate("/technician");
    } catch (err) {
      setErrorMessage("Error submitting maintenance record.");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="start-maintenance">
      <button className="back-button" onClick={() => navigate("/technician")}>← Back</button>
      <h2>🛠️ Start Maintenance</h2>

      <input
        type="text"
        placeholder="Enter Machine ID"
        value={machineId}
        onChange={(e) => setMachineId(e.target.value)}
        disabled={maintenanceStarted}
      />
      <button onClick={handleFetchMachine} disabled={maintenanceStarted || isSubmitting}>
        {isSubmitting ? "Loading..." : "Fetch Machine"}
      </button>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      {machineData && (
        <div className="machine-details">
          <p><strong>Section:</strong> {machineData.section}</p>
          <p><strong>Division:</strong> {machineData.division}</p>
          <p><strong>Pole No:</strong> {machineData.poleNo}</p>
          <p><strong>From KM:</strong> {machineData.fromKm}</p>
          <p><strong>To KM:</strong> {machineData.toKm}</p>
        </div>
      )}

      {!maintenanceStarted && machineData && (
        <button className="start-btn" onClick={handleStartMaintenance}>
          Start Maintenance
        </button>
      )}

      {maintenanceStarted && !formVisible && (
        <button className="end-btn" onClick={() => setFormVisible(true)}>
          End Maintenance
        </button>
      )}

      {maintenanceStarted && (
        <button className="complete-btn" onClick={handleNavigateToComplete}>
          Complete Maintenance →
        </button>
      )}
    </div>
  );
}
