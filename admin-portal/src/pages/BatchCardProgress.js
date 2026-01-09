import React, { useEffect, useState } from "react";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import { useNavigate } from "react-router-dom";
import "./BatchCardProgress.css";
import API_BASE_URL from "../apiConfig";

export default function JobOrderProgress() {
  const [jobOrders, setJobOrders] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [entriesToShow, setEntriesToShow] = useState(10);

  const navigate = useNavigate();

  useEffect(() => {
    fetchInProgressJobOrders();
  }, []);

  const fetchInProgressJobOrders = async () => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get(`${API_BASE_URL}/api/job-cards/in-progress/details`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      const transformedData = (res.data || []).map((order) => ({
        jobCardNumber: order.job_card_number,
        quantity: order.quantity,
        producedCount: order.produced_count ?? 0,
        qcDoneCount: order.qc_done_count ?? 0,
        dispatchedCount: order.dispatched_count ?? 0,
        firstMachineSerial: order.first_machine_serial,
        lastMachineSerial: order.last_machine_serial,
        machines: [],
        machineSerialNumbers: [],
      }));

      setJobOrders(transformedData);
    } catch (error) {
      console.error("Error fetching job order progress:", error);
    }
  };

  const handleBalancedQCClick = (jobCardNumber) => {
    const machinesForJobCard = jobOrders.find(order => order.jobCardNumber === jobCardNumber)?.machines || [];
    navigate("/admin/pending-quality-check", { state: { jobCardNumber, machines: machinesForJobCard } });
  };

  const printJobCard = (jobCard) => {
    const printWindow = window.open("", "_blank");
    const printContent = `
      <html>
      <head>
        <title>Job Card - ${jobCard.jobCardNumber}</title>
        <style>
          @page { size: A4 landscape; margin: 10mm; }
          body { font-family: Arial, sans-serif; padding: 20px; }
          .header { text-align: center; }
          .header h1 { margin: 0; font-size: 28px; }
          .header h2 { margin: 5px 0 20px 0; font-size: 22px; }
          .details-inline { display: flex; flex-wrap: wrap; gap: 30px; font-size: 14px; margin-bottom: 20px; }
          .details-inline p { margin: 4px 0; min-width: 180px; }
          .machine-table { width: 100%; border-collapse: collapse; font-size: 14px; table-layout: fixed; }
          .machine-table th, .machine-table td { border: 1px solid #000; padding: 6px; text-align: center; vertical-align: top; overflow-wrap: break-word; }
          .machine-table th { background-color: #f2f2f2; }
          .footer { margin-top: 40px; display: flex; justify-content: space-between; font-size: 13px; }
          .footer div { width: 30%; text-align: center; margin-top: 60px; }
        </style>
      </head>
      <body>
        <div class="header">
          <h1>Chakradhar Industries</h1>
          <h2>Job Card - TBL</h2>
        </div>
        <div class="details-inline">
          <p><strong>Job Order No:</strong> ${jobCard.jobCardNumber}</p>
          <p><strong>Quantity:</strong> ${jobCard.quantity}</p>
        </div>
        <h4>Generated Machine Serial Numbers:</h4>
        <table class="machine-table">
          <thead>
            <tr>
              <th>#</th>
              <th>Serial Number</th>
              <th>Motor No.</th>
              <th>Sensor No.</th>
              <th>Applicator No.</th>
              <th>Battery No.</th>
              <th>Solar Charger Controller No.</th>
              <th>Solar Panel No. 1</th>
              <th>Solar Panel No. 2</th>
              <th>Cabinet No.</th>
              <th>Batch Counter No.</th>
              <th>MCB No.</th>
              <th>Gear Pump No.</th>
            </tr>
          </thead>
          <tbody>
            ${(jobCard.machineSerialNumbers ?? []).map(
              (serial, index) => `
                <tr>
                  <td>${index + 1}</td>
                  <td>${serial}</td>
                  <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                  <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                </tr>
              `
            ).join("")}
          </tbody>
        </table>
        <div class="footer">
          <div>Prepared By</div>
          <div>Checked By</div>
          <div>Approved By</div>
        </div>
        <script>window.onload = function() { window.print(); }</script>
      </body>
      </html>
    `;
    printWindow.document.write(printContent);
    printWindow.document.close();
  };

  const filteredOrders = jobOrders.filter(order =>
    order.jobCardNumber.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const visibleOrders = filteredOrders.slice(0, entriesToShow);

  return (
    <AdminLayout>
      <div className="technician-list-container">
        <h2 className="page-heading">Production Status</h2>

        <div style={{ marginBottom: "10px", display: "flex", gap: "20px", alignItems: "center" }}>
          <input
            type="text"
            placeholder="Search by Batch Card No."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            style={{ padding: "6px", width: "250px", fontSize: "14px" }}
          />

          <div>
            <label>Show </label>
            <select
              value={entriesToShow}
              onChange={(e) => setEntriesToShow(Number(e.target.value))}
              style={{ padding: "6px", borderRadius: "6px" }}
            >
              <option value={10}>10</option>
              <option value={25}>25</option>
              <option value={50}>50</option>
              <option value={100}>100</option>
              <option value={1000}>1000</option>
              <option value={filteredOrders.length}>All</option>
            </select>
            <label> entries</label>
          </div>
        </div>

        <div className="table-scroll-container">
          <table className="technician-table">
            <thead>
              <tr>
                <th>Batch Card No</th>
                <th>Batch Card Quantity</th>
                <th>Accept Qty</th>
                <th>Pending Production</th>
                <th>Balanced Final QC</th>
                <th>Pending Dispatch</th>
                <th>Dispatched</th>
                <th>Print</th>
              </tr>
            </thead>
            <tbody>
              {visibleOrders.length === 0 ? (
                <tr>
                  <td colSpan="8" style={{ textAlign: "center" }}>No job orders found.</td>
                </tr>
              ) : (
                visibleOrders.map((order) => {
                  const produced = order.producedCount;
                  const qcDone = order.qcDoneCount;
                  const dispatched = order.dispatchedCount;
                  const pendingQC = produced - qcDone;
                  const pendingDispatch = qcDone - dispatched;
                  const pendingProduction = order.quantity - produced;

                  return (
                   <tr key={order.jobCardNumber}>
                    <td data-label="Batch Card No">
                      {order.jobCardNumber}
                    </td>

                    <td data-label="Batch Quantity">
                      {order.quantity}
                    </td>

                    <td data-label="Accepted Qty">
                      {produced}
                    </td>

                    <td data-label="Pending Production">
                      {pendingProduction}
                    </td>

                    <td data-label="Balanced Final QC">
                      <button onClick={() => handleBalancedQCClick(order.jobCardNumber)}>
                        {pendingQC}
                      </button>
                    </td>

                    <td data-label="Pending Dispatch">
                      {pendingDispatch}
                    </td>

                    <td data-label="Dispatched">
                      {dispatched}
                    </td>

                    <td data-label="Print">
                      <button
                        className="print-button"
                        onClick={() => printJobCard(order)}
                      >
                        🖨️
                      </button>
                    </td>
                  </tr>

                  );
                })
              )}
            </tbody>
          </table>
        </div>

        {filteredOrders.length > 0 && (
          <div className="summary-scroll-wrapper">
            <div className="summary-bar">
              <div><strong>Total Batch Cards</strong><p>{filteredOrders.length}</p></div>
              <div><strong>Total Batch Card Quantity</strong><p>{filteredOrders.reduce((sum, o) => sum + (o.quantity || 0), 0)}</p></div>
              <div><strong>Total Accepted Qty</strong><p>{filteredOrders.reduce((sum, o) => sum + (o.producedCount || 0), 0)}</p></div>
              <div><strong>Total Pending Production</strong><p>{filteredOrders.reduce((sum, o) => sum + ((o.quantity || 0) - (o.producedCount || 0)), 0)}</p></div>
              <div><strong>Total Balance Final QC</strong><p>{filteredOrders.reduce((sum, o) => sum + ((o.producedCount || 0) - (o.qcDoneCount || 0)), 0)}</p></div>
              <div><strong>Total Pending Dispatch</strong><p>{filteredOrders.reduce((sum, o) => sum + ((o.qcDoneCount || 0) - (o.dispatchedCount || 0)), 0)}</p></div>
              <div><strong>Total Dispatched</strong><p>{filteredOrders.reduce((sum, o) => sum + (o.dispatchedCount || 0), 0)}</p></div>
            </div>
          </div>
        )}
      </div>
    </AdminLayout>
  );
}
