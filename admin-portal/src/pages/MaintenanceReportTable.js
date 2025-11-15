import React, { useEffect, useState } from "react";
import AdminLayout from "../components/AdminLayout";
import axios from "axios";
import "./MaintenanceReportTable.css";
import API_BASE_URL from "../apiConfig";

const MaintenanceReportTable = () => {
  const [filters, setFilters] = useState({
    fromDate: "",
    toDate: "",
    modelNo: "",
  });

  const [records, setRecords] = useState([]);

  const getSanitizedFilters = () => ({
    modelNo: filters.modelNo.trim() || null,
    fromDate: filters.fromDate || null,
    toDate: filters.toDate || null,
  });

  const fetchMaintenanceData = async () => {
    try {
      const token = localStorage.getItem("token");
      const sanitizedFilters = getSanitizedFilters();

      const response = await axios.post(
        `${API_BASE_URL}/api/maintenance/list`,
        sanitizedFilters,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setRecords(response.data);
    } catch (error) {
      console.error("Error fetching maintenance report:", error);
    }
  };

  useEffect(() => {
    fetchMaintenanceData();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFilters({ ...filters, [name]: value });
  };

  const handleFilterSubmit = (e) => {
    e.preventDefault();
    fetchMaintenanceData();
  };

  const groupByModel = (data) => {
    const grouped = {};
    data.forEach((record) => {
      const modelKey = record.modelNo || "Unknown";
      if (!grouped[modelKey]) grouped[modelKey] = [];
      grouped[modelKey].push(record);
    });
    return grouped;
  };

  const getMonthlyCounts = (records) => {
    const counts = {};
    records.forEach(({ maintenanceDate }) => {
      if (!maintenanceDate) return;
      const month = new Date(maintenanceDate).toLocaleString("default", {
        month: "short",
        year: "numeric",
      });
      counts[month] = (counts[month] || 0) + 1;
    });
    return counts;
  };

  const handleExportCSV = async () => {
  try {
    const token = localStorage.getItem("token");
    const sanitizedFilters = getSanitizedFilters();

    const response = await axios.post(
      `${API_BASE_URL}/api/maintenance/list`,
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

    // Define column headers
    const headers = [
      "Model No",
      "Visit Date",
      "Start Date",
      "End Date",
      "Technician ID",
      "Division",
      "Section",
      "Status",
      "Grease Level",
      "Sensor",
      "Applicator",
      "Battery Reading",
      "Solar Panel Reading",
      "Time Count",
      "Wheel Count"
    ];

    const formatDate = (dateStr) =>
      dateStr ? new Date(dateStr).toLocaleDateString() : "";

    const rows = exportData.map((rec) => {
      const status =
        rec.maintenanceStarted && rec.maintenanceEnded
          ? "Complete"
          : rec.maintenanceStarted
          ? "In Progress"
          : "Pending";

      return [
        rec.modelNo || "",
        formatDate(rec.maintenanceDate),
        formatDate(rec.maintenanceStarted),
        formatDate(rec.maintenanceEnded),
        rec.technicianUserId || "",
        rec.division || "",
        rec.section || "",
        status,
        rec.greaseLevel || "",
        rec.sensor || "",
        rec.applicator || "",
        rec.batteryReading || "",
        rec.solarPanelReading || "",
        rec.timeCount ?? "",
        rec.wheelCount ?? ""
      ];
    });

    const csvContent = [
      headers.join(","), // CSV header row
      ...rows.map((row) => row.map((val) => `"${val}"`).join(",")),
    ].join("\n");

    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", "maintenance-report.csv");
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  } catch (error) {
    console.error("CSV Export Error:", error);
  }
};


  const groupedRecords = groupByModel(records);

  return (
    <AdminLayout>
      <div className="dispatch-report-container">
        <h2>Maintenance Report</h2>

        <form className="filter-form" onSubmit={handleFilterSubmit}>
          <div className="filter-row">
            <div className="form-group">
              <label htmlFor="fromDate">From Date</label>
              <input
                type="date"
                name="fromDate"
                value={filters.fromDate}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="toDate">To Date</label>
              <input
                type="date"
                name="toDate"
                value={filters.toDate}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="modelNo">Model No</label>
              <input
                type="text"
                name="modelNo"
                value={filters.modelNo}
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

        {Object.keys(groupedRecords).length === 0 ? (
          <p>No maintenance records found.</p>
        ) : (
          Object.entries(groupedRecords).map(([modelNo, records]) => {
            const monthlyCounts = getMonthlyCounts(records);
            return (
              <div key={modelNo} className="maintenance-group">
                <h3>Model No: {modelNo}</h3>
                <p>
                  <strong>Monthly Visits:</strong>{" "}
                  {Object.entries(monthlyCounts)
                    .map(([month, count]) => `${month}: ${count}`)
                    .join(", ")}
                </p>
                <table className="dispatch-table">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Visit Date</th>
                      <th>Start Date</th>
                      <th>End Date</th>
                      <th>Division</th>
                      <th>Section</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {records.map((rec, idx) => (
                      <tr key={idx}>
                        <td>{idx + 1}</td>
                        <td>{rec.maintenanceDate?.split("T")[0] || "N/A"}</td>
                        <td>{rec.maintenanceStarted?.split("T")[0] || "N/A"}</td>
                        <td>{rec.maintenanceEnded?.split("T")[0] || "N/A"}</td>
                        <td>{rec.division || "N/A"}</td>
                        <td>{rec.section || "N/A"}</td>
                        <td
                            className={`status ${
                                rec.status === "Completed"
                                ? "completed"
                                : rec.status === "In Process"
                                ? "in-process"
                                : "unknown"
                            }`}
                            >
                            {rec.status || "N/A"}
                            </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            );
          })
        )}
      </div>
    </AdminLayout>
  );
};

export default MaintenanceReportTable;
