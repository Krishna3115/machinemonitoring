import React, { useEffect, useState } from "react";
import AdminLayout from "../components/AdminLayout";
import axios from "axios";
import "./InstallationReport.css"; // Reuse CSS
import API_BASE_URL from "../apiConfig";

const InstallationReportTable = () => {
  const [filters, setFilters] = useState({
    fromDate: "",
    toDate: "",
    modelNo: "",
    division: "",
    section: "",
  });

  const [records, setRecords] = useState([]);

  // Common sanitization logic
  const getSanitizedFilters = () => {
    return {
      ...filters,
      modelNo: filters.modelNo.trim() || null,
      division: filters.division.trim() || null,
      section: filters.section.trim() || null,
      fromDate: filters.fromDate || null,
      toDate: filters.toDate || null,
    };
  };

  const fetchInstallationData = async () => {
    try {
      const token = localStorage.getItem("token");
      const sanitizedFilters = getSanitizedFilters();

      const response = await axios.post(
        `${API_BASE_URL}/api/reports/list`,
        sanitizedFilters,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setRecords(response.data);
    } catch (error) {
      console.error("Error fetching installation report:", error);
    }
  };

  useEffect(() => {
    fetchInstallationData();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFilters({ ...filters, [name]: value });
  };

  const handleFilterSubmit = (e) => {
    e.preventDefault();
    fetchInstallationData();
  };

  const handleExportCSV = async () => {
    try {
      const token = localStorage.getItem("token");
      const sanitizedFilters = getSanitizedFilters();

      const response = await axios.post(
        `${API_BASE_URL}/api/reports/list`,
        sanitizedFilters,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      const exportData = response.data;

      if (!exportData.length) {
        alert("No data to export.");
        return;
      }

      const headers = Object.keys(exportData[0]);
      const csvRows = [
        headers.join(","), // header row
        ...exportData.map((row) =>
          headers.map((field) => `"${row[field] ?? ""}"`).join(",")
        ),
      ];

      const blob = new Blob([csvRows.join("\n")], {
        type: "text/csv;charset=utf-8;",
      });
      const url = URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.setAttribute("href", url);
      link.setAttribute("download", "installation-report.csv");
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } catch (error) {
      console.error("CSV Export Error:", error);
      alert("Failed to export installation report.");
    }
  };

  return (
    <AdminLayout>
      <div className="dispatch-report-container">
        <h2>Installation Report</h2>

        <form className="filter-form" onSubmit={handleFilterSubmit}>
          <div className="filter-row">
            <div className="form-group">
              <label htmlFor="fromDate">From Date</label>
              <input
                type="date"
                id="fromDate"
                name="fromDate"
                value={filters.fromDate}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="toDate">To Date</label>
              <input
                type="date"
                id="toDate"
                name="toDate"
                value={filters.toDate}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="modelNo">Model No</label>
              <input
                type="text"
                id="modelNo"
                name="modelNo"
                value={filters.modelNo}
                onChange={handleInputChange}
              />
            </div>
          </div>

          <div className="filter-row">
            <div className="form-group">
              <label htmlFor="division">Division</label>
              <input
                type="text"
                id="division"
                name="division"
                value={filters.division}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="section">Section</label>
              <input
                type="text"
                id="section"
                name="section"
                value={filters.section}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group button-group">
              <button type="submit">Apply Filters</button>
            </div>
          </div>

          <div className="export-options">
            <button type="button" onClick={handleExportCSV}>
              Export CSV
            </button>
          </div>
        </form>

        {records.length === 0 ? (
          <p>No installation records found.</p>
        ) : (
          <table className="dispatch-table">
            <thead>
              <tr>
                <th>Sr.</th>
                <th>Model No.</th>
                <th>Install Start</th>
                <th>Install End</th>
                <th>Section</th>
                <th>Pole No.</th>
                <th>From KM</th>
                <th>To KM</th>
                <th>Wheel Count</th>
                <th>Time Count</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {records.map((record, index) => (
                <tr key={index}>
                  <td>{record.srNo || index + 1}</td>
                  <td>{record.modelNo}</td>
                  <td>
                    {record.installationStarted
                      ? record.installationStarted.split("T")[0]
                      : "N/A"}
                  </td>
                  <td>
                    {record.installationEnded
                      ? record.installationEnded.split("T")[0]
                      : "N/A"}
                  </td>
                  <td>{record.section}</td>
                  <td>{record.poleNo}</td>
                  <td>{record.fromKm}</td>
                  <td>{record.toKm}</td>
                  <td>{record.wheelCount}</td>
                  <td>{record.timeCount}</td>
                  <td
                    className={`status ${record.status
                      .replace(/\s+/g, "-")
                      .toLowerCase()}`}
                  >
                    {record.status}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </AdminLayout>
  );
};

export default InstallationReportTable;
