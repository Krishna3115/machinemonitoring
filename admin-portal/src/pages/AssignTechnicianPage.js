// AssignTechnicianPage.jsx
import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import API_BASE_URL from "../apiConfig";
import "./AssignTechnicianPage.css";

export default function AssignTechnicianPage() {
  const location = useLocation();
  const { state } = location; // receive modelNo from previous page

  const [technicians, setTechnicians] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const [form, setForm] = useState({
    machineNumber: "",
    technicianId: "",
    taskType: "",
    scheduleDate: "",
    section: "",
    division: "",
    serialNumber: "",
  });

  // Auto-clear messages
  useEffect(() => {
    if (message) {
      const timer = setTimeout(() => setMessage(""), 4000);
      return () => clearTimeout(timer);
    }
  }, [message]);

  // Fetch all technicians
  useEffect(() => {
    const fetchTechnicians = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(
          `${API_BASE_URL}/api/users/technicians/dropdown`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );
        setTechnicians(response.data);
      } catch (error) {
        console.error("Error fetching technicians:", error);
        setMessage("❌ Failed to load technicians.");
      }
    };
    fetchTechnicians();
  }, []);

  // Auto-fill section & division if saved earlier
  useEffect(() => {
    const storedSection = localStorage.getItem("section");
    const storedDivision = localStorage.getItem("division");

    setForm((prev) => ({
      ...prev,
      section: storedSection || "",
      division: storedDivision || "",
    }));
  }, []);

  // Auto-fill machineNumber from DeliveredMachines
  useEffect(() => {
    if (state?.modelNo) {
      setForm((prev) => ({
        ...prev,
        machineNumber: state.modelNo,
      }));
    }
  }, [state]);

  // Handle field changes
  const handleChange = (e) => {
    const { name, value } = e.target;

    // Don't clear machine number for installation
    if (name === "taskType") {
      setForm((prev) => ({
        ...prev,
        [name]: value,
        serialNumber: value === "Installation" ? "" : prev.serialNumber,
      }));
    } else {
      setForm((prev) => ({ ...prev, [name]: value }));
    }
  };

  // Submit
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    const {
      machineNumber,
      technicianId,
      taskType,
      scheduleDate,
      section,
      division,
      serialNumber,
    } = form;

    if (!technicianId || !taskType || !scheduleDate || !section || !division) {
      setMessage("❌ Please fill all required fields.");
      setLoading(false);
      return;
    }

    // For Maintenance / Complaint: machineNumber and serialNumber MUST exist
    if (
      (taskType === "Maintenance" || taskType === "Customer Complaint") &&
      (!machineNumber || !serialNumber)
    ) {
      setMessage("❌ Please provide model number and serial number.");
      setLoading(false);
      return;
    }

    const assignedById = localStorage.getItem("userId");

    const payload = {
      assignedById: parseInt(assignedById, 10),
      technicianId: parseInt(technicianId, 10),
      machineNumber: machineNumber, // never null now
      taskType,
      scheduleDate,
      section,
      division,
      serialNumber: serialNumber || "",
    };

    try {
      const token = localStorage.getItem("token");
      const response = await axios.post(
        `${API_BASE_URL}/api/assign-technician`,
        payload,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      const successMsg =
        typeof response.data === "string"
          ? response.data
          : response.data.message || "Technician assigned successfully.";

      setMessage(`✅ ${successMsg}`);

      // Reset form
      setForm({
        machineNumber: "",
        technicianId: "",
        taskType: "",
        scheduleDate: "",
        section: "",
        division: "",
        serialNumber: "",
      });
    } catch (error) {
      console.error("Assignment failed:", error.response || error.message);
      setMessage(
        error.response?.data?.message ||
          "❌ Error assigning technician. Please try again."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <AdminLayout>
      <div className="assign-tech-container">
        <form onSubmit={handleSubmit} className="assign-form">
          <h2 className="assign-title">🛠️ Assign Technician</h2>

          <label>
            Section:
            <input
              type="text"
              name="section"
              value={form.section}
              onChange={handleChange}
              required
            />
          </label>

          <label>
            Division:
            <input
              type="text"
              name="division"
              value={form.division}
              onChange={handleChange}
              required
            />
          </label>

          <label>
            Technician:
            <select
              name="technicianId"
              value={form.technicianId}
              onChange={handleChange}
              required
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
            Task Type:
            <select
              name="taskType"
              value={form.taskType}
              onChange={handleChange}
              required
            >
              <option value="">-- Select Task --</option>
              <option value="Installation">Installation</option>
              <option value="Maintenance">Maintenance</option>
              <option value="Customer Complaint">Customer Complaint</option>
            </select>
          </label>

          {(form.taskType === "Maintenance" ||
            form.taskType === "Customer Complaint") && (
            <>
              <label>
                Model Number:
                <input
                  type="text"
                  name="machineNumber"
                  value={form.machineNumber}
                  onChange={handleChange}
                  required
                />
              </label>

              <label>
                Machine Serial No:
                <input
                  type="text"
                  name="serialNumber"
                  value={form.serialNumber}
                  onChange={handleChange}
                  required
                />
              </label>
            </>
          )}

          <label>
            Schedule Date:
            <input
              type="date"
              name="scheduleDate"
              value={form.scheduleDate}
              onChange={handleChange}
              required
            />
          </label>

          <button type="submit" disabled={loading}>
            {loading ? "Assigning..." : "Assign"}
          </button>

          {message && (
            <p className={`message ${message.startsWith("✅") ? "success" : "error"}`}>
              {message}
            </p>
          )}
        </form>
      </div>
    </AdminLayout>
  );
}
