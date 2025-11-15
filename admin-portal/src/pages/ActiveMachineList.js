import React, { useEffect, useState } from "react";
import AdminLayout from "../components/AdminLayout";
import axios from "axios";
import API_BASE_URL from "../apiConfig";
import "./TechnicianList.css";

// Helper function to format date arrays like [2025, 9, 8, 14, 17]
const formatDate = (arr) => {
  if (!arr || !arr.length) return "N/A";
  const [year, month, day] = arr;
  return `${year}-${month.toString().padStart(2, "0")}-${day.toString().padStart(2, "0")}`;
};

export default function ActiveMachinesList() {
  const [machines, setMachines] = useState([]);
  const [filteredMachines, setFilteredMachines] = useState([]);
  const [search, setSearch] = useState({
    modelNo: "",
    division: "",
    section: ""
  });

  useEffect(() => {
    fetchActiveMachines();
  }, []);

  const fetchActiveMachines = async () => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get(
        `${API_BASE_URL}/api/machines/status/COMPLETE/with-installation`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setMachines(res.data);
      setFilteredMachines(res.data);
    } catch (err) {
      console.error("Error fetching active machines", err);
    }
  };

  const handleSearchChange = (e) => {
    const { name, value } = e.target;
    const updatedSearch = { ...search, [name]: value };
    setSearch(updatedSearch);

    const filtered = machines.filter((m) =>
      (!updatedSearch.modelNo || m.model_no?.toLowerCase().includes(updatedSearch.modelNo.toLowerCase())) &&
      (!updatedSearch.division || m.division?.toLowerCase().includes(updatedSearch.division.toLowerCase())) &&
      (!updatedSearch.section || m.section?.toLowerCase().includes(updatedSearch.section.toLowerCase()))
    );
    setFilteredMachines(filtered);
  };

  return (
    <AdminLayout>
      <div className="technician-list-container">
        <h2>Active Machines List</h2>

        {/* Search bar */}
        <div className="search-bar" style={{ marginBottom: "20px" }}>
          <input
            type="text"
            name="modelNo"
            placeholder="Search by Model No"
            value={search.modelNo}
            onChange={handleSearchChange}
          />
          <input
            type="text"
            name="division"
            placeholder="Search by Division"
            value={search.division}
            onChange={handleSearchChange}
          />
          <input
            type="text"
            name="section"
            placeholder="Search by Section"
            value={search.section}
            onChange={handleSearchChange}
          />
        </div>

        <table className="technician-table">
          <thead>
            <tr>
              <th>Machine Serial No.</th>
              <th>Machine Type</th>
              <th>Division</th>
              <th>Section</th>
              <th>Status</th>
              <th>Delivered Date</th>
              <th>Installation Date</th>
              <th>Warranty End</th>
            </tr>
          </thead>
          <tbody>
            {filteredMachines.length === 0 ? (
              <tr>
                <td colSpan="8" style={{ textAlign: "center" }}>
                  No active machines found.
                </td>
              </tr>
            ) : (
              filteredMachines.map((machine) => {
                // If your API provides warrantyDaysRemaining, keep it. Otherwise ignore
                const warrantyDays = machine.warrantyDaysRemaining || 0;
                const warrantyStatus =
                  warrantyDays > 0
                    ? `${Math.floor(warrantyDays / 30)} months`
                    : <span className="expired">Expired</span>;

                return (
                  <tr key={machine.id}>
                    <td>{machine.model_no}</td>
                    <td>{machine.machine_name}</td>
                    <td>{machine.division}</td>
                    <td>{machine.section}</td>
                    <td className="status-complete">{machine.status}</td>
                    <td>{formatDate(machine.delivered_date)}</td>
                    <td>{formatDate(machine.installation_date)}</td>
                    <td>{formatDate(machine.warranty_end_date)}</td>
                  </tr>
                );
              })
            )}
          </tbody>
        </table>
      </div>
    </AdminLayout>
  );
}
