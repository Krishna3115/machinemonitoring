import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./StartInstallation.css";
import API_BASE_URL from "../../apiConfig";

export default function StartInstallation() {
  const navigate = useNavigate();
  const [modelNo, setModelNo] = useState("");
  const [machineData, setMachineData] = useState(null);
  const [installationStarted, setInstallationStarted] = useState(false);
  const [formVisible, setFormVisible] = useState(false);
  const [endRemarks, setEndRemarks] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  // ✅ Corrected: use "userId" (the same key saved in login)
  const technicianId = localStorage.getItem("userId");

  useEffect(() => {
    const fetchInstallationStatus = async () => {
      if (!technicianId) {
        console.warn("Technician ID not found in localStorage!");
        return;
      }

      try {
        const response = await axios.get(
          `${API_BASE_URL}/api/installations/installing-by-technician?technicianId=${technicianId}`
        );

        if (response.data.length > 0) {
          const ongoingInstallation = response.data[0];
          setModelNo(ongoingInstallation.modelNo);
          setMachineData({
            section: ongoingInstallation.section,
            division: ongoingInstallation.division,
          });
          setInstallationStarted(true);
        }
      } catch (error) {
        console.error("Error fetching installation status:", error);
      }
    };

    fetchInstallationStatus();
  }, [technicianId]);

  const handleModelSubmit = async () => {
    if (isSubmitting) return;

    setIsSubmitting(true);
    setErrorMessage("");

    try {
      const response = await axios.get(
        `${API_BASE_URL}/api/machines/${modelNo}/division-section`
      );
      setMachineData(response.data);
    } catch (error) {
      setErrorMessage("Machine not found or installation already in progress.");
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleStartInstallation = async () => {
    if (isSubmitting || !technicianId) return;

    setIsSubmitting(true);
    setErrorMessage("");

    try {
      await axios.post(
        `${API_BASE_URL}/api/installations/start?modelNo=${modelNo}&technicianId=${technicianId}`
      );
      setInstallationStarted(true);
    } catch (error) {
      setErrorMessage("Error starting installation.");
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleEndInstallation = async () => {
    if (isSubmitting) return;

    setIsSubmitting(true);
    setErrorMessage("");

    try {
      await axios.post(`${API_BASE_URL}/api/installations/complete`, {
        modelNo,
        remarks: endRemarks,
      });

      alert("Installation completed successfully!");
      setModelNo("");
      setMachineData(null);
      setInstallationStarted(false);
      setFormVisible(false);
      setEndRemarks("");
    } catch (error) {
      setErrorMessage("Error completing installation.");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="start-installation">
      <button className="back-button" onClick={() => navigate("/technician")}>
        ← Back
      </button>

      <h2>🔧 Start Installation</h2>

      <input
        type="text"
        placeholder="Enter Machine Serial Number"
        value={modelNo}
        onChange={(e) => setModelNo(e.target.value)}
        disabled={isSubmitting || installationStarted}
      />
      <button
        onClick={handleModelSubmit}
        disabled={isSubmitting || installationStarted}
      >
        {isSubmitting ? "Loading..." : "Fetch Machine"}
      </button>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      {machineData && (
        <div className="machine-details">
          <p>
            <strong>Section:</strong> {machineData.section}
          </p>
          <p>
            <strong>Division:</strong> {machineData.division}
          </p>
        </div>
      )}

      {!installationStarted && machineData && (
        <button
          className="start-btn"
          onClick={handleStartInstallation}
          disabled={isSubmitting}
        >
          {isSubmitting ? "Starting..." : "Start Installation"}
        </button>
      )}

      {installationStarted && !formVisible && (
        <button
          className="complete-btn"
          onClick={() =>
            navigate("/technician/complete-installation", {
              state: { modelNo },
            })
          }
        >
          Complete Installation →
        </button>
      )}

      {formVisible && (
        <div className="end-installation-form">
          <h4>Complete Installation</h4>
          <textarea
            placeholder="Enter remarks or notes..."
            value={endRemarks}
            onChange={(e) => setEndRemarks(e.target.value)}
            disabled={isSubmitting}
          />
          <button
            className="submit-btn"
            onClick={handleEndInstallation}
            disabled={isSubmitting}
          >
            {isSubmitting ? "Submitting..." : "Submit"}
          </button>
        </div>
      )}
    </div>
  );
}
