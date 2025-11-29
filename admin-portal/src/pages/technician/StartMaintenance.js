import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./StartMaintenance.css";
import API_BASE_URL from "../../apiConfig";

export default function StartMaintenance() {
  const navigate = useNavigate();
  const [modelNo, setModelNo] = useState("");
  const [machineData, setMachineData] = useState(null);
  const [inspectionId, setInspectionId] = useState(null);
  const [maintenanceStarted, setMaintenanceStarted] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const technicianId = localStorage.getItem("userId");

  // ----------------------------------------------------
  // FETCH MACHINE DETAILS
  // ----------------------------------------------------
  const handleFetchMachine = async () => {
    if (!modelNo) {
      setErrorMessage("Machine ID is required.");
      return;
    }

    setIsSubmitting(true);
    setErrorMessage("");

    try {
      const response = await axios.get(`${API_BASE_URL}/api/machines/location/${modelNo}`);
      setMachineData(response.data);
    } catch (err) {
      setErrorMessage("Error fetching machine details.");
    } finally {
      setIsSubmitting(false);
    }
  };

  // ----------------------------------------------------
  // START MAINTENANCE
  // ----------------------------------------------------
  const handleStartMaintenance = async () => {
    if (!modelNo) {
      setErrorMessage("Machine ID is required.");
      return;
    }

    setIsSubmitting(true);
    setErrorMessage("");

    try {
      const response = await axios.post(
        `${API_BASE_URL}/api/machines/start-maintenance`,
        null,
        {
          params: {
            modelNo: modelNo,
            technicianId: technicianId,
          },
        }
      );

      setInspectionId(response.data.inspectionId); // SAVE inspectionId
      setMaintenanceStarted(true);
    } catch (err) {
      setErrorMessage("Failed to start maintenance.");
    } finally {
      setIsSubmitting(false);
    }
  };

  // ----------------------------------------------------
  // END MAINTENANCE
  // ----------------------------------------------------
  const handleEndMaintenance = async () => {
    setIsSubmitting(true);
    setErrorMessage("");

    try {
      await axios.post(
        `${API_BASE_URL}/api/machines/end-maintenance`,
        null,
        {
          params: {
            modelNo: modelNo,
            technicianId: technicianId,
          },
        }
      );

      alert("Maintenance session ended successfully.");
    } catch (err) {
      setErrorMessage("Failed to end maintenance.");
    } finally {
      setIsSubmitting(false);
    }
  };

  // ----------------------------------------------------
  // GO TO COMPLETE MAINTENANCE
  // ----------------------------------------------------
  const handleNavigateToComplete = () => {
  navigate("/technician/complete-maintenance", {
    state: {
      machineId: machineData.machine_id,   // ✅ FIXED: send machineId
      modelNo: machineData.model_no,
      inspectionId: inspectionId,
    },
  });
};


  return (
    <div className="start-maintenance">
      <button className="back-button" onClick={() => navigate("/technician")}>
        ← Back
      </button>

      <h2>🛠️ Start Maintenance</h2>

      <input
        type="text"
        placeholder="Enter Machine Model Number"
        value={modelNo}
        onChange={(e) => setModelNo(e.target.value)}
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

      {maintenanceStarted && (
        <>
          <button className="end-btn" onClick={handleEndMaintenance}>
            End Maintenance
          </button>

          <button className="complete-btn" onClick={handleNavigateToComplete}>
            Complete Maintenance →
          </button>
        </>
      )}
    </div>
  );
}
