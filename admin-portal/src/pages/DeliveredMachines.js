// DeliveredMachines.jsx
import React, { useEffect, useState } from "react";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import API_BASE_URL from "../apiConfig";
import { useNavigate } from "react-router-dom";
import "./DeliveredMachines.css";

export default function DeliveredMachines() {
  const [machines, setMachines] = useState([]);
  const [selectedMachines, setSelectedMachines] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchDeliveredMachines();
  }, []);

  const fetchDeliveredMachines = async () => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get(`${API_BASE_URL}/api/machines/status/DELIVERED`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setMachines(res.data);
    } catch (err) {
      console.error("Failed to fetch delivered machines", err);
    }
  };

  const handleCheckboxChange = (machineId) => {
    setSelectedMachines((prev) =>
      prev.includes(machineId)
        ? prev.filter((id) => id !== machineId)
        : [...prev, machineId]
    );
  };

  const handleSelectAll = (checked) => {
    if (checked) {
      setSelectedMachines(machines.map((m) => Number(m.id)));
    } else {
      setSelectedMachines([]);
    }
  };

  const goToAssignTechnician = () => {
    if (selectedMachines.length === 0) {
      alert("Please select at least one machine.");
      return;
    }

    // Ensure all IDs are numbers
    const machineIdsNumbers = selectedMachines.map((id) => Number(id));
    console.log("Selected machine IDs:", machineIdsNumbers);

    navigate("/admin/assign-tech", { state: { machineIds: machineIdsNumbers } });
  };

  const parseBackendDate = (dateArray) => {
    if (!dateArray || !Array.isArray(dateArray)) return null;
    const [year, month, day, hour = 0, minute = 0] = dateArray;
    return new Date(year, month - 1, day, hour, minute);
  };

  const formatDate = (dateArray) => {
    const date = parseBackendDate(dateArray);
    if (!date) return "N/A";
    return `${String(date.getDate()).padStart(2, "0")}-${String(
      date.getMonth() + 1
    ).padStart(2, "0")}-${date.getFullYear()}`;
  };

  const calculateDaysSinceDelivery = (array) => {
    const delivered = parseBackendDate(array);
    if (!delivered) return "-";
    const now = new Date();
    return Math.floor((now - delivered) / (1000 * 60 * 60 * 24));
  };

  return (
    <AdminLayout>
      <div className="delivered-machines-container">
        <h2>Machines Pending for Installation</h2>

        {selectedMachines.length > 0 && (
          <div className="assign-selected-section">
            <button onClick={goToAssignTechnician}>
              Assign Technician to {selectedMachines.length} machine(s)
            </button>
          </div>
        )}

        <table className="delivered-table">
          <thead>
            <tr>
              <th>
                <input
                  type="checkbox"
                  onChange={(e) => handleSelectAll(e.target.checked)}
                  checked={
                    selectedMachines.length === machines.length && machines.length > 0
                  }
                />
              </th>
              <th>Machine Serial No</th>
              <th>Machine Name</th>
              <th>Delivered Location</th>
              <th>Delivered Date</th>
              <th>Days Since Delivery</th>
            </tr>
          </thead>

          <tbody>
            {machines.length === 0 ? (
              <tr>
                <td colSpan="6" style={{ textAlign: "center" }}>
                  No delivered machines found
                </td>
              </tr>
            ) : (
              machines.map((machine) => (
                <tr key={machine.id}>
                  <td>
                    <input
                      type="checkbox"
                      checked={selectedMachines.includes(Number(machine.id))}
                      onChange={() => handleCheckboxChange(Number(machine.id))}
                    />
                  </td>
                  <td>{machine.model_no}</td>
                  <td>{machine.machine_name}</td>
                  <td>{machine.division}</td>
                  <td>{formatDate(machine.delivered_date)}</td>
                  <td>{calculateDaysSinceDelivery(machine.delivered_date)} days</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </AdminLayout>
  );
}
