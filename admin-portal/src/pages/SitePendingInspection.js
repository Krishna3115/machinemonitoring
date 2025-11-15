import React, { useEffect, useState } from "react";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import "./SitePendingInspection.css";
import API_BASE_URL from "../apiConfig";

// Helper to format date arrays like [2025, 9, 8, 14, 17] to YYYY-MM-DD
const formatDate = (arr) => {
  if (!arr || !arr.length) return "N/A";
  const [year, month, day] = arr;
  return `${year}-${month.toString().padStart(2, "0")}-${day.toString().padStart(2, "0")}`;
};

// Convert date array to JS Date object
const dateArrayToDate = (arr) => {
  if (!arr || !arr.length) return null;
  const [year, month, day, hour = 0, minute = 0] = arr;
  return new Date(year, month - 1, day, hour, minute);
};

export default function SiteInspectionPendingList() {
  const [machines, setMachines] = useState([]);
  const [formStates, setFormStates] = useState({});

  useEffect(() => {
    fetchPendingMachines();
  }, []);

  const fetchPendingMachines = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get(
        `${API_BASE_URL}/api/machines/site-inspection/pending`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setMachines(response.data);
    } catch (error) {
      console.error("Error fetching pending inspections:", error);
    }
  };

  const handleChange = (id, field, value) => {
    setFormStates((prev) => ({
      ...prev,
      [id]: {
        ...prev[id],
        [field]: value,
      },
    }));
  };

  const handleActionChange = (id, action) => {
    setFormStates((prev) => ({
      ...prev,
      [id]: {
        ...(prev[id] || {}),
        action,
        inspectionDate: "",
        reinspectionDecidedDate: "",
        reinspectionRemark: "",
      },
    }));
  };

  const handleSubmit = async (id) => {
    const token = localStorage.getItem("token");
    const form = formStates[id] || {};

    if (!form.action) {
      alert("Please choose an action before submitting.");
      return;
    }

    if (form.action === "reinspection") {
      if (!form.reinspectionDecidedDate || !form.reinspectionRemark) {
        alert("Please enter both date and remark for reinspection.");
        return;
      }
    }

    if (form.action === "markDone") {
      if (!form.inspectionDate) {
        alert("Please enter inspection date to mark as done.");
        return;
      }
    }

    try {
      await axios.put(
        `${API_BASE_URL}/api/machines/${id}/site-inspection`,
        form,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert("Submission successful.");
      fetchPendingMachines();
      setFormStates((prev) => {
        const newState = { ...prev };
        delete newState[id];
        return newState;
      });
    } catch (error) {
      console.error("Error updating inspection:", error);
      alert("Failed to submit. Check console for details.");
    }
  };

  const calculateDaysPending = (installEndArr) => {
    const end = dateArrayToDate(installEndArr);
    if (!end) return "N/A";
    const now = new Date();
    const diffMs = now - end;
    const diffDays = Math.floor(diffMs / (1000 * 60 * 60 * 24));
    return diffDays >= 0 ? `${diffDays} day(s)` : "Invalid date";
  };

  const displayStatus = (machine) => machine.inspection_status || "Pending";

  return (
    <AdminLayout>
      <div className="technician-list-container">
        <h2>Site Final Inspection Pending Machines</h2>
        <table className="technician-table">
          <thead>
            <tr>
              <th>Model No</th>
              <th>Machine Name</th>
              <th>Section</th>
              <th>Delivered Date</th>
              <th>Installation Ended</th>
              <th>Pending Days</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {machines.length === 0 && (
              <tr>
                <td colSpan="8" style={{ textAlign: "center" }}>
                  No pending inspections.
                </td>
              </tr>
            )}
            {machines.map((machine) => {
              const form = formStates[machine.id] || {};
              return (
                <tr key={machine.id}>
                  <td>{machine.model_no}</td>
                  <td>{machine.machine_name}</td>
                  <td>{machine.section}</td>
                  <td>{formatDate(machine.delivered_date)}</td>
                  <td>{formatDate(machine.installation_ended)}</td>
                  <td>{calculateDaysPending(machine.installation_ended)}</td>
                  <td>{displayStatus(machine)}</td>
                  <td>
                    <select
                      value={form.action || ""}
                      onChange={(e) => handleActionChange(machine.id, e.target.value)}
                    >
                      <option value="">Choose Action</option>
                      <option value="markDone">Mark as Done</option>
                      <option value="reinspection">Reinspection Required</option>
                    </select>

                    {form.action === "markDone" && (
                      <input
                        type="datetime-local"
                        value={form.inspectionDate || ""}
                        onChange={(e) =>
                          handleChange(machine.id, "inspectionDate", e.target.value)
                        }
                      />
                    )}

                    {form.action === "reinspection" && (
                      <div>
                        <label>Reinspection Date:</label>
                        <input
                          type="datetime-local"
                          value={form.reinspectionDecidedDate || ""}
                          onChange={(e) =>
                            handleChange(machine.id, "reinspectionDecidedDate", e.target.value)
                          }
                        />

                        <label>Remark:</label>
                        <textarea
                          placeholder="Reason for reinspection"
                          value={form.reinspectionRemark || ""}
                          onChange={(e) =>
                            handleChange(machine.id, "reinspectionRemark", e.target.value)
                          }
                        />
                      </div>
                    )}

                    <button
                      className="action-button unblock"
                      onClick={() => handleSubmit(machine.id)}
                    >
                      Submit
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </AdminLayout>
  );
}
