import React, { useEffect, useState } from "react";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import "./InstallationInProcess.css";
import API_BASE_URL from "../apiConfig";

export default function InstallationInProcess() {
  const [installations, setInstallations] = useState([]);

  useEffect(() => {
    fetchInProgressInstallations();
  }, []);

  const fetchInProgressInstallations = async () => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get(`${API_BASE_URL}/api/machines/installing`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setInstallations(res.data);
    } catch (error) {
      console.error("Error fetching installations in progress:", error);
    }
  };

  // Convert array to formatted date DD/MM/YYYY
  const formatDate = (arr) => {
    if (!Array.isArray(arr)) return "Invalid Date";

    const [year, month, day] = arr; 
    const date = new Date(year, month - 1, day);

    const dd = String(date.getDate()).padStart(2, "0");
    const mm = String(date.getMonth() + 1).padStart(2, "0");
    const yyyy = date.getFullYear();

    return `${dd}/${mm}/${yyyy}`;
  };

  // Calculate days since installation started
  const getDaysSinceStart = (arr) => {
    if (!Array.isArray(arr)) return "N/A";

    const [year, month, day] = arr;
    const startDate = new Date(year, month - 1, day);
    const today = new Date();

    const diffTime = today - startDate;
    const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));

    return diffDays === 0 ? "Today" : `${diffDays} day(s) ago`;
  };

  return (
    <AdminLayout>
      <div className="technician-list-container">
        <h2>Installation In Progress</h2>

        <table className="technician-table">
          <thead>
            <tr>
              <th>Machine Serial No</th>
              <th>Technician</th>
              <th>Start Date</th>
              <th>Days Since Start</th>
              <th>Division</th>
              <th>Section</th>
            </tr>
          </thead>

          <tbody>
            {installations.length === 0 ? (
              <tr>
                <td colSpan="6" style={{ textAlign: "center" }}>
                  No installations in progress.
                </td>
              </tr>
            ) : (
              installations.map((item) => (
                <tr key={item.machineId}>
                  <td>{item.model_no}</td>
                  <td>{item.technicianName}</td>
                  <td>{formatDate(item.installation_started)}</td>
                  <td>{getDaysSinceStart(item.installation_started)}</td>
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
