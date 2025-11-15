import React, { useEffect, useState } from "react";
import AdminLayout from "../components/AdminLayout";
import axios from "axios";
import API_BASE_URL from "../apiConfig";
import "./InstallationInProcess.css";

export default function UnderMaintenanceList() {
  const [machines, setMachines] = useState([]);

  useEffect(() => {
    fetchUnderMaintenanceMachines();
  }, []);

  const fetchUnderMaintenanceMachines = async () => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get(
        `${API_BASE_URL}/api/machines/status/under-maintenance-list`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setMachines(res.data);
    } catch (error) {
      console.error("Error fetching under maintenance machines:", error);
    }
  };

  const getDaysAgo = (dateStr) => {
    if (!dateStr) return "-";
    const date = new Date(dateStr);
    const today = new Date();

    // Remove the time portion for comparison (only compare dates)
    today.setHours(0, 0, 0, 0);
    date.setHours(0, 0, 0, 0);

    const diffTime = today - date;
    const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));

    // If the date is today, return "Today"
    return diffDays === 0 ? "Today" : `${diffDays} day(s) ago`;
  };

  return (
    <AdminLayout>
      <div className="technician-list-container">
        <h2>Machines Under Maintenance</h2>
        <table className="technician-table">
          <thead>
            <tr>
              <th>Model No</th>
              <th>Status</th>
              <th>Latest Maintenance</th>
              <th>Complaint Date</th>
              <th>Vandalism Date</th>
              <th>Division</th>
              <th>Section</th>
            </tr>
          </thead>
          <tbody>
            {machines.length === 0 ? (
              <tr>
                <td colSpan="7" style={{ textAlign: "center" }}>
                  No machines under maintenance.
                </td>
              </tr>
            ) : (
              machines.map((item, index) => (
                <tr key={index}>
                  <td>{item.modelNo}</td>
                  <td>{item.status}</td>
                  <td>
                    {item.latestMaintenanceDate
                      ? new Date(item.latestMaintenanceDate).toLocaleString()
                      : "-"}
                  </td>
                  <td>{getDaysAgo(item.latestComplaintDate)}</td>
                  <td>{getDaysAgo(item.latestVandalismDate)}</td>
                  <td>{item.division}</td>
                  <td>{item.section}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </AdminLayout>
  );
}
