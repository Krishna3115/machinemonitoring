import React, { useState, useEffect } from "react";
import AdminLayout from "../components/AdminLayout";
import axios from "axios";
import "./DispatchReportTable.css"; // create this file
import API_BASE_URL from "../apiConfig";

const DispatchReportTable = () => {
  const [filters, setFilters] = useState({
    poNumber: "",
    division: "",
    section: "",
    dispatchDate: "",
    fromDate: "",
    toDate: "",


  });

  const [machines, setMachines] = useState([]);
  const [exportAll, setExportAll] = useState(false);


  const fetchReportData = async () => {
   try {
    const token = localStorage.getItem("token");

    const sanitizedFilters = {
      ...filters,
      poNumber: filters.poNumber.trim() || null,
      division: filters.division.trim() || null,
      section: filters.section.trim() || null,
      fromDate: filters.fromDate || null,
      toDate: filters.toDate || null,
       exportAll: exportAll
    };

    const response = await axios.post(`${API_BASE_URL}/api/reports/dispatch-reports`, sanitizedFilters, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    setMachines(response.data);
  } catch (error) {
    console.error("Error fetching dispatch report:", error);
  }
  };

  useEffect(() => {
    fetchReportData();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFilters({ ...filters, [name]: value });
  };

  const handleFilterSubmit = (e) => {
    e.preventDefault();
    fetchReportData();
  };

  const handleExportCSV = async () => {
  let exportData = [];

  try {
    const token = localStorage.getItem("token");

    if (exportAll) {
      const response = await axios.get(`${API_BASE_URL}/api/reports/export-dispatch-report`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      exportData = response.data;
    } else {
      exportData = machines;
    }

    if (!exportData.length) {
      alert("No data to export.");
      return;
    }

    const headers = Object.keys(exportData[0]);
    const csvRows = [
      headers.join(","),
      ...exportData.map(row =>
        headers.map(field => `"${row[field] ?? ""}"`).join(",")
      ),
    ];

    const blob = new Blob([csvRows.join("\n")], { type: "text/csv;charset=utf-8;" });
    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.setAttribute("href", url);
    link.setAttribute("download", "dispatch-report.csv");
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  } catch (error) {
    console.error("CSV Export Error:", error);
    alert("Failed to export data.");
  }
};


  return (
    <AdminLayout>
      <div className="dispatch-report-container">
        <h2>Dispatch Report</h2>

        <form className="filter-form" onSubmit={handleFilterSubmit}>
         <div className="filter-row">
    <div className="form-group">
      <label htmlFor="fromDate">From Dispatch Date</label>
      <input
        type="date"
        id="fromDate"
        name="fromDate"
        value={filters.fromDate}
        onChange={handleInputChange}
      />
    </div>
    <div className="form-group">
      <label htmlFor="toDate">To Dispatch Date</label>
      <input
        type="date"
        id="toDate"
        name="toDate"
        value={filters.toDate}
        onChange={handleInputChange}
      />
    </div>
    <div className="form-group">
      <label htmlFor="section">Section</label>
      <input
        type="text"
        id="section"
        name="section"
        placeholder="Enter Section"
        value={filters.section}
        onChange={handleInputChange}
      />
    </div>
  </div>

  <div className="filter-row">
    <div className="form-group">
      <label htmlFor="poNumber">PO Number</label>
      <input
        type="text"
        id="poNumber"
        name="poNumber"
        placeholder="Enter PO Number"
        value={filters.poNumber}
        onChange={handleInputChange}
      />
    </div>
    <div className="form-group">
      <label htmlFor="division">Division</label>
      <input
        type="text"
        id="division"
        name="division"
        placeholder="Enter Division"
        value={filters.division}
        onChange={handleInputChange}
      />
    </div>
    <div className="form-group button-group">
      <button type="submit">Apply Filters</button>
    </div>
  </div>
  
  <div className="export-options">
  <label className="checkbox-label">
    <input
      type="checkbox"
      id="exportAll"
      checked={exportAll}
      onChange={(e) => setExportAll(e.target.checked)}
    />
    <span>All Data</span>
  </label>
  <button type="button" onClick={handleExportCSV}>Export CSV</button>
</div>


        </form>

        {machines.length === 0 ? (
          <p>No records found.</p>
        ) : (
          <table className="dispatch-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Model No.</th>
                <th>Machine Name</th>
                <th>Status</th>
                <th>Dispatch Date</th>
                <th>Delivered Date</th>
                <th>Division</th>
          
                <th>PO Number</th>
                <th>CDI Inspection</th>

              </tr>
            </thead>
            <tbody>
              {machines.map((machine) => (
                <tr key={machine.id}>
                  <td>{machine.id}</td>
                  <td>{machine.modelNo}</td>
                  <td>{machine.machineName}</td>
                  <td className={`status ${machine.status.toLowerCase()}`}>{machine.status}</td>
                  <td>{machine.dispatchDate?.split("T")[0]}</td>
                  <td>{machine.deliveredDate ? machine.deliveredDate.split("T")[0] : "N/A"}</td>
                  <td>{machine.division}</td>
                 
                
                  <td>{machine.poNumber}</td>
                  <td>{machine.finalInspectionDoneBy || "N/A"}</td>

                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </AdminLayout>
  );
};

export default DispatchReportTable;
