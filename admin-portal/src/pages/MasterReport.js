import React, { useEffect, useState } from "react";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import "./MasterReport.css";
import API_BASE_URL from "../apiConfig";

export default function MasterReport() {
  const [reportData, setReportData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [error, setError] = useState(null);

  // ✅ Converts backend date array to readable date (DD-MM-YYYY)
  const formatDate = (dateArray) => {
    if (!dateArray || dateArray.length < 3) return "-";
    const [year, month, day, hour = 0, minute = 0, second = 0] = dateArray;
    const date = new Date(year, month - 1, day, hour, minute, second);
    const d = String(date.getDate()).padStart(2, "0");
    const m = String(date.getMonth() + 1).padStart(2, "0");
    const y = date.getFullYear();
    return `${d}-${m}-${y}`;
  };

  useEffect(() => {
    fetchMasterReport();
  }, []);

  const fetchMasterReport = async () => {
    try {
      setLoading(true);
      setError(null);
      const token = localStorage.getItem("token");
      const res = await axios.get(`${API_BASE_URL}/api/master-report`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setReportData(res.data);
    } catch (error) {
      console.error("Error fetching master report:", error);
      setError("Failed to fetch master report data.");
    } finally {
      setLoading(false);
    }
  };

  // ✅ Filter based on search input
  const filteredData = reportData.filter((item) => {
    const search = searchTerm.toLowerCase();
    return (
      item.model_no?.toLowerCase().includes(search) ||
      item.po_number?.toLowerCase().includes(search) ||
      item.location?.toLowerCase().includes(search)
    );
  });

  // ✅ Download CSV function
  const downloadCSV = () => {
    if (filteredData.length === 0) {
      alert("No data available to export!");
      return;
    }

    const headers = [
      "Sr. No",
      "Model No",
      "Machine Name",
      "Location",
      "Status",
      "PO Number",
      "PO Date",
      "PO Qty",
      "Planned Qty",
      "Production Start",
      "Production End",
      "Dispatch Date",
      "Delivered Date",
      "Installation Started",
      "Installation Ended",
      "Division/Section",
      "Curve No",
      "Pole No",
      "KM Range",
      "Radius",
      "Machine Status",
      "Grease Level",
      "Wheel Count",
      "Time Count",
      "Remarks",
    ];

    const rows = filteredData.map((item, index) => [
      index + 1,
      item.model_no,
      item.machine_name,
      item.location,
      item.status,
      item.po_number,
      formatDate(item.po_date),
      item.po_quantity,
      item.planned_quantity,
      formatDate(item.production_start_date),
      formatDate(item.production_end_date),
      formatDate(item.dispatch_date),
      formatDate(item.delivered_date),
      formatDate(item.installation_started),
      formatDate(item.installation_ended),
      item.section || "-",
      item.curve_no || "-",
      item.pole_no || "-",
      `${item.from_km || "-"} - ${item.to_km || "-"}`,
      item.rh_lh_radius || "-",
      item.machine_status || "-",
      item.grease_level ? `${item.grease_level} %` : "N/A",
      item.wheel_count || "-",
      item.time_count || "-",
      item.remarks || "-",
    ]);

    const csvContent = [headers, ...rows]
      .map((row) =>
        row
          .map((cell) => `"${String(cell).replace(/"/g, '""')}"`)
          .join(",")
      )
      .join("\n");

    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.download = `MasterReport_${new Date().toISOString().slice(0, 10)}.csv`;
    link.click();
    URL.revokeObjectURL(url);
  };

  return (
    <AdminLayout>
      <div className="master-report-container">
        <h2 className="report-title">📊 Master Machine Report</h2>

        <div className="top-bar">
          <div className="search-box">
            <input
              type="text"
              placeholder="🔍 Search by Machine Serial No / PO Number / Location..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>

          <button className="download-btn" onClick={downloadCSV}>
            ⬇️ Download CSV
          </button>
        </div>

        {loading ? (
          <p className="loading-text">Loading report...</p>
        ) : error ? (
          <p className="error-text">{error}</p>
        ) : (
          <div className="table-wrapper">
            <table className="master-report-table">
              <thead>
                <tr>
                  <th>Sr. No</th>
                  <th>Machine Serial No</th>
                  <th>Machine Name</th>
                  <th>Location</th>
                  <th>Status</th>
                  <th>PO Number</th>
                  <th>PO Date</th>
                  <th>PO Qty</th>
                  <th>Planned Qty</th>
                  <th>Production Start</th>
                  <th>Production End</th>
                  <th>Dispatch Date</th>
                  <th>Delivered Date</th>
                  <th>Installation Started</th>
                  <th>Installation Ended</th>
                  <th>Division/Section</th>
                  <th>Curve No</th>
                  <th>Pole No</th>
                  <th>KM Range</th>
                  <th>Radius</th>
                  <th>Machine Status</th>
                  <th>Grease Level</th>
                  <th>Wheel Count</th>
                  <th>Time Count</th>
                  <th>Remarks</th>
                </tr>
              </thead>

              <tbody>
                {filteredData.length === 0 ? (
                  <tr>
                    <td colSpan="25" className="no-data">
                      No data found.
                    </td>
                  </tr>
                ) : (
                  filteredData.map((item, index) => (
                    <tr key={index}>
                      <td>{index + 1}</td>
                      <td>{item.model_no}</td>
                      <td>{item.machine_name}</td>
                      <td>{item.location}</td>
                      <td className={`status ${item.status?.toLowerCase()}`}>
                        {item.status}
                      </td>
                      <td>{item.po_number}</td>
                      <td>{formatDate(item.po_date)}</td>
                      <td>{item.po_quantity}</td>
                      <td>{item.planned_quantity}</td>
                      <td>{formatDate(item.production_start_date)}</td>
                      <td>{formatDate(item.production_end_date)}</td>
                      <td>{formatDate(item.dispatch_date)}</td>
                      <td>{formatDate(item.delivered_date)}</td>
                      <td>{formatDate(item.installation_started)}</td>
                      <td>{formatDate(item.installation_ended)}</td>
                      <td>{item.section || "-"}</td>
                      <td>{item.curve_no || "-"}</td>
                      <td>{item.pole_no || "-"}</td>
                      <td>
                        {item.from_km || "-"} - {item.to_km || "-"}
                      </td>
                      <td>{item.rh_lh_radius || "-"}</td>
                      <td>{item.machine_status || "-"}</td>
                      <td>
                        {item.grease_level ? `${item.grease_level} %` : "N/A"}
                      </td>
                      <td>{item.wheel_count || "-"}</td>
                      <td>{item.time_count || "-"}</td>
                      <td>{item.remarks || "-"}</td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </AdminLayout>
  );
}
