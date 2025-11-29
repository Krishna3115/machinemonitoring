// AssignTechnicianPage.jsx
import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import API_BASE_URL from "../apiConfig";
import "./AssignTechnicianPage.css";

export default function AssignTechnicianPage() {
  const location = useLocation();
  const navigate = useNavigate();
  const { state } = location;

  const [technicians, setTechnicians] = useState([]);
  const [selectedTechnician, setSelectedTechnician] = useState("");
  const [startDate, setStartDate] = useState("");
  const [targetDate, setTargetDate] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  // Array of machine IDs from previous page
  const machineIds = state?.machineIds || [];

  // Logged-in user ID (Assigned By)
  const assignedById = Number(localStorage.getItem("userId"));
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      setMessage("❌ User not authenticated");
      return;
    }
    fetchTechnicians();
  }, [token]);

  // Fetch all technicians with "installation" designation
  const fetchTechnicians = async () => {
    try {
      const res = await axios.get(
        `${API_BASE_URL}/api/users/technicians/dropdown?designation=installation`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setTechnicians(res.data);
    } catch (err) {
      console.error("Failed to fetch technicians:", err);
      setMessage("❌ Failed to load technicians.");
    }
  };

  // Handle assigning technician to multiple machines
  const handleAssign = async () => {
    // Validate required fields
    if (!selectedTechnician || !startDate || !targetDate || !assignedById || machineIds.length === 0) {
      setMessage("❌ All fields must be provided before assigning!");
      return;
    }

    try {
      setLoading(true);

      // Convert IDs to numbers
      const technicianId = Number(selectedTechnician);
      const machineIdsNumber = machineIds.map(id => Number(id));

      // Debug log
      console.log("Assigning technician with payload:", {
        machine_ids: machineIdsNumber,
        technician_id: technicianId,
        assigned_by_id: assignedById,
        start_date: startDate,
        target_date: targetDate,
        task_type: "Installation",
      });

      // POST request to backend
      await axios.post(
  `${API_BASE_URL}/api/assign-installation-tasks`,
  {
    machineIds: machineIdsNumber,
    technicianId: technicianId,
    assignedById: assignedById,
    startDate: startDate,
    targetDate: targetDate,
    taskType: "Installation",
  },
  { headers: { Authorization: `Bearer ${token}` } }
);


      setMessage(`✅ Technician assigned to ${machineIds.length} machine(s) successfully!`);

      // Redirect after 2 seconds
      setTimeout(() => navigate("/admin/delivered-machines"), 2000);
    } catch (err) {
      console.error("Failed to assign technician:", err);
      setMessage("❌ Failed to assign technician. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <AdminLayout>
      <div className="assign-tech-container">
        <h2>Assign Technician to {machineIds.length} Machine(s)</h2>

        {message && (
          <p className={`message ${message.startsWith("✅") ? "success" : "error"}`}>
            {message}
          </p>
        )}

        <label>
          Technician:
          <select
            value={selectedTechnician}
            onChange={(e) => setSelectedTechnician(e.target.value)}
          >
            <option value="">-- Select Technician --</option>
            {technicians.map((tech) => (
              <option key={tech.id} value={tech.id}>
                {tech.name}
              </option>
            ))}
          </select>
        </label>

        <label>
          Start Date:
          <input
            type="date"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
          />
        </label>

        <label>
          Target Date:
          <input
            type="date"
            value={targetDate}
            onChange={(e) => setTargetDate(e.target.value)}
          />
        </label>

        <div className="buttons">
          <button onClick={handleAssign} disabled={loading}>
            {loading ? "Assigning..." : `Assign Technician`}
          </button>
          <button onClick={() => navigate("/admin/delivered-machines")}>
            Cancel
          </button>
        </div>
      </div>
    </AdminLayout>
  );
}
