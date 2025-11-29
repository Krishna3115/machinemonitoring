import React, { useEffect, useState } from "react";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import "./PendingQualityCheck.css";
import API_BASE_URL from "../apiConfig";
import Toast from "../components/Toast";


export default function PendingQualityCheck() {
  const [machines, setMachines] = useState([]);
  const [selected, setSelected] = useState([]);
  const [qcFile, setQcFile] = useState(null);
  const [uploaded, setUploaded] = useState(false);
  const [toast, setToast] = useState({ message: "", type: "success" });
const showToast = (message, type = "success") => setToast({ message, type });


  useEffect(() => {
    fetchPendingQC();
  }, []);

  const fetchPendingQC = async () => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get(
        `${API_BASE_URL}/api/machines-production/production/pending-quality-check`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setMachines(res.data);
      setUploaded(false);
      setQcFile(null);
      setSelected([]);
    } catch (err) {
      console.error("Failed to fetch machines pending QC:", err);
    }
  };

  const handleSelectAll = (e) => {
    setSelected(e.target.checked ? machines.map((m) => m.id) : []);
  };

  const handleCheckboxChange = (id) => {
    setSelected((prev) =>
      prev.includes(id) ? prev.filter((mid) => mid !== id) : [...prev, id]
    );
  };

  const handleUploadQcReport = () => {
    if (!selected.length) return showToast("Please select at least one machine.", "error");
    if (!qcFile) return showToast("Please upload a PDF QC report.", "error");
    setUploaded(true);
  };

  const handleFinalInspection = async () => {
    if (!uploaded) return;
    if (!qcFile) return showToast("QC file is missing.", "error");


    try {
      const token = localStorage.getItem("token");
      const formData = new FormData();
      formData.append("qcFile", qcFile);
      formData.append("inspectionDate", new Date().toISOString());
      formData.append("machineIds", JSON.stringify(selected));

      await axios.post(`${API_BASE_URL}/api/machines-production/qc-complete`, formData, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "multipart/form-data",
        },
      });

     showToast("✅ Final inspection complete. Machines marked READY_TO_DISPATCH.", "success");

      fetchPendingQC();
    } catch (err) {
      console.error("Error completing inspection:", err);
      showToast("❌ Failed to finalize QC.", "error");

    }
  };

  // 🔥 UPDATED PRINT FUNCTION
const printSelectedMachines = () => {
  const selectedMachines = machines.filter((m) => selected.includes(m.id));
  if (!selectedMachines.length) return;

  const printWindow = window.open("", "_blank");
  if (!printWindow) return;

  // Robust date formatting function
  const formatDate = (dateInput) => {
  if (!dateInput) return "";

  let date;

  // If it's an array like [year, month, day, hour, minute, second]
  if (Array.isArray(dateInput)) {
    const [y, m, d] = dateInput;
    if (!y || !m || !d) return "";
    // Month in JS Date is 0-indexed
    date = new Date(y, m - 1, d);
  } 
  // If it's a string or number
  else if (typeof dateInput === "string" || typeof dateInput === "number") {
    date = new Date(dateInput);
  } 
  // If it's already a Date object
  else if (dateInput instanceof Date) {
    date = dateInput;
  } 
  else {
    return "";
  }

  if (isNaN(date.getTime())) return "";

  const day = String(date.getDate()).padStart(2, "0");
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const year = date.getFullYear();
  return `${day}-${month}-${year}`;
};


  const offlineAssyMapping = {
    "Junction Box Assy (JB)": { batchNo: "junctionBoxBatchNo", batchDate: "junctionBoxBatchDate" },
    "Sensor Assy": { batchNo: "sensorAssyBatchNo", batchDate: "sensorAssyBatchDate" },
    "Tank Motor Pump Assy (TMP)": { batchNo: "tmpAssyBatchNo", batchDate: "tmpAssyBatchDate" },
    "Applicator Assy": { batchNo: "applicatorAssyBatchNo", batchDate: "applicatorAssyBatchDate" },
    "Solar Panel Assy": { batchNo: "solarPanelAssyBatchNo", batchDate: "solarPanelAssyBatchDate" },
  };

  const leftParts = [
    { desc: "Cabinet Assy MS 50L", serialKey: "cabinetNo" },
    { desc: "Proximity Sensor", serialKey: "sensorNo" },
    { desc: "DC Motor 24V 50 Watt", serialKey: "motorNo" },
    { desc: "Hydraulic Pump 15 LPM", serialKey: "gearPumpNo" },
  ];

  const rightParts = [
    { desc: "Charge controller 24V 20AMP", serialKey: "solarChargeControllerNo" },
    { desc: "Batch Counter 24V DC", serialKey: "batchCounterNo" },
    { desc: "Applicator Set", serialKey: "applicatorNo" },
    { desc: "Li ion Battery 24V 20AH", serialKey: "batteryNo" },
  ];

  const offlineAssyDetails = [
    "Junction Box Assy (JB)",
    "Sensor Assy",
    "Tank Motor Pump Assy (TMP)",
    "Applicator Assy",
    "Solar Panel Assy",
  ];

  const finalInspectionRows = [
    { part: "Cabinet Assy MS 50L", specification: "Surface finish, Alignment", testMethod: "Visual" },
    { part: "Proximity Sensor", specification: "LED indicator & Signal", testMethod: "Visual" },
    { part: "DC Motor 24V 50 Watt", specification: "Clockwise in left position", testMethod: "Visual" },
    { part: "Hydraulic Pump 15 LPM", specification: "Fitment Check", testMethod: "Visual" },
    { part: "Li ion Battery 24V 20AH", specification: "27 - 29 DC Volt ( + 2V)", testMethod: "Multimeter" },
    { part: "Charge controller 24V 20AMP", specification: "26 - 29 DC Volt ( + 2V)", testMethod: "Multimeter" },
    { part: "Junction Box Assy (JB)", specification: "MCB tri & Reset to ZERO", testMethod: "Visual" },
    { part: "Applicator Set", specification: "Welding Check at Corner", testMethod: "Visual" },
  ];

  const styles = `
    <style>
      @page { size: A4 landscape; margin: 5mm; }
      body { font-family: Arial, sans-serif; font-size: 15px; margin: 0; padding: 0; }
      table { width: 100%; border-collapse: collapse; margin-bottom: 5px; table-layout: fixed; page-break-inside: avoid; }
      th, td { border: 1px solid black; padding: 4px 5px; vertical-align: top; word-wrap: break-word; font-size: 12px; }
      th { background: #f0f0f0; font-weight: bold; text-align: left; }
      .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 5px; }
      .header .title { font-weight: bold; font-size: 18px; text-align: center; flex: 1; }
      .header .date { font-weight: bold; font-size: 13px; }
      .section-title { font-weight: bold; font-size: 15px; margin: 7px 0 4px 0; }
      .checkbox { text-align: center; }
      input[type="checkbox"] { width: 12px; height: 12px; }
      .nowrap { white-space: nowrap; }
      .page {
        position: relative;
        page-break-after: always;
        padding-bottom: 50px;
        transform: scale(0.99);
        transform-origin: top left;
      }
      .footer-jobcard {
        position: absolute;
        bottom: 5px;
        right: 10px;
        font-weight: bold;
        font-size: 18px;
      }
      .footer-center {
        position: absolute;
        bottom: 5px;
        left: 50%;
        transform: translateX(-50%);
        font-size: 10px;
        text-align: center;
      }
      table, tr, td, th { page-break-inside: avoid; }
    </style>
  `;

  let pagesHtml = "";

  for (const machine of selectedMachines) {
    const jobCard = machine.machineSerialNo?.match(/\d+$/)?.[0] || machine.machineSerialNo || "XXXX";
    const machineType = machine.machineType || "TBL-Electronics";
    const machineSrNo = machine.machineSerialNo || "TBL-ELE-XXX";

    const formattedDate = formatDate(machine.created_at || new Date());

    pagesHtml += `
      <div class="page">
        <div class="header">
          <div class="title">JOB CARD - ${jobCard}</div>
          <div class="date">Date: ${formattedDate}</div>
        </div>

        <table>
          <tr>
            <td class="nowrap">Machine Type: ${machineType}</td>
            <td class="nowrap">Machine Sr. No.: ${machineSrNo}</td>
            <td class="nowrap">Batch No.: ${machine.jobCardNo || ""}</td>
          </tr>
        </table>

        <div class="section-title">Critical Parts Details:</div>
        <table>
          <thead>
            <tr>
              <th>Part Description</th>
              <th>Part Serial No.</th>
              <th>Entry Done By</th>
              <th>Part Description</th>
              <th>Part Serial No.</th>
            </tr>
          </thead>
          <tbody>
            ${leftParts.map((lp, i) => {
              const entryByFullName = machine.submitted_by_name || "";
              const entryByFirstName = entryByFullName.split(" ")[0];
              return `
                <tr>
                  <td>${lp.desc}</td>
                  <td>${machine[lp.serialKey] || ""}</td>
                  <td>${entryByFirstName}</td>
                  <td>${rightParts[i]?.desc || ""}</td>
                  <td>${machine[rightParts[i]?.serialKey] || ""}</td>
                </tr>
              `;
            }).join("")}
          </tbody>
        </table>

        <div class="section-title">Offline Sub Assy Details</div>
        <table>
          <thead>
            <tr>
              <th>Offline Assy Details</th>
              <th>Batch No</th>
              <th>Batch Date</th>
              <th>Check By</th>
              <th>Approve By</th>
              <th>Remark</th>
            </tr>
          </thead>
          <tbody>
            ${offlineAssyDetails.map(name => {
              const mapping = offlineAssyMapping[name];
              const batchNo = machine[mapping.batchNo] || "";
              const batchDate = mapping.batchDate ? formatDate(machine[mapping.batchDate]) : "";
              return `
                <tr>
                  <td>${name}</td>
                  <td>${batchNo}</td>
                  <td>${batchDate}</td>
                  <td></td>
                  <td></td>
                  <td></td>
                </tr>
              `;
            }).join("")}
          </tbody>
        </table>

        <div class="section-title">Final Inspection / PDI</div>
        <table>
          <thead>
            <tr>
              <th>Part / Assy details</th>
              <th>Specification</th>
              <th>Test Method</th>
              <th>Checked By</th>
              <th>Observation</th>
              <th>Remark</th>
            </tr>
          </thead>
          <tbody>
            ${finalInspectionRows.map(row => `
              <tr>
                <td>${row.part}</td>
                <td>${row.specification}</td>
                <td>${row.testMethod}</td>
                <td></td><td></td><td></td>
              </tr>
            `).join("")}
          </tbody>
        </table>

        <table>
          <tbody>
            <tr>
              <td>All Test result summary</td>
              <td>Approved</td>
              <td class="checkbox"><input type="checkbox" disabled></td>
              <td>Rework</td>
              <td class="checkbox"><input type="checkbox" disabled></td>
              <td>Hold</td>
              <td class="checkbox"><input type="checkbox" disabled></td>
              <td>Rejected</td>
              <td class="checkbox"><input type="checkbox" disabled></td>
            </tr>
            <tr>
              <td>Remark if any:-</td>
              <td colspan="8" style="height: 40px;"></td>
            </tr>
          </tbody>
        </table>

        <div class="footer-jobcard">Job Card NO:- ${jobCard}</div>
        <div class="footer-center">Format No. CIL/TBL-FIR/004 Rev: 05</div>
      </div>
    `;
  }

  const content = `
    <html>
      <head><title>Print Job Card for PDI</title>${styles}</head>
      <body>
        ${pagesHtml}
        <script>window.onload = () => window.print();</script>
      </body>
    </html>
  `;

  printWindow.document.write(content);
  printWindow.document.close();
};



  return (
    <AdminLayout>
      <div className="technician-list-container">
        <h2>Machines Pending for Final Quality Check</h2>
        <table className="technician-table">
          <thead>
            <tr>
              <th style={{ width: "30px" }}>
                <input
                  type="checkbox"
                  checked={selected.length === machines.length}
                  onChange={handleSelectAll}
                />
              </th>
              <th>Machine Serial No</th>
              <th>Batch Card No</th>
              <th>Manufacturing Date</th>
              <th>Days Since Manufacturing</th>  {/* New column */}

            </tr>
          </thead>
          <tbody>
            {machines.length === 0 ? (
              <tr>
                <td colSpan="6" style={{ textAlign: "center" }}>
                  No machines pending quality check.
                </td>
              </tr>
            ) : (
              machines.map((m) => (
                <tr key={m.id}>
                  <td>
                    <input
                      type="checkbox"
                      checked={selected.includes(m.id)}
                      onChange={() => handleCheckboxChange(m.id)}
                    />
                  </td>
                  <td>{m.machineSerialNo}</td>
                  <td>{m.jobCardNo}</td>
                  {/* <td>{m.motorNo || "–"}</td>
                  <td>{m.batteryNo || "–"}</td>
                  <td>{m.cabinetNo || "–"}</td> */}
                  <td>
                      {m.created_at
                        ? `${String(m.created_at[2]).padStart(2, "0")}/${String(m.created_at[1]).padStart(2, "0")}/${m.created_at[0]}`
                        : "–"}
                  </td>
                  <td>
                      {m.created_at
                        ? Math.floor(
                            (new Date() - new Date(m.created_at[0], m.created_at[1] - 1, m.created_at[2])) / (1000 * 60 * 60 * 24)
                          )
                        : "–"}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>

        {selected.length > 0 && (
          <div style={{ marginTop: "20px", textAlign: "center" }}>
            <button
              className="action-button"
              onClick={printSelectedMachines}
              style={{ marginRight: "10px" }}
            >
              🖨️ Print Job Card for PDI
            </button>

            <input
              type="file"
              accept="application/pdf"
              onChange={(e) => setQcFile(e.target.files[0])}
              style={{ display: "block", margin: "10px auto" }}
            />

            {!uploaded ? (
              <button
                className="action-button"
                onClick={handleUploadQcReport}
              >
                📤 Upload Job Card & PDI Report
              </button>
            ) : (
              <button
                className="action-button"
                onClick={handleFinalInspection}
                style={{ marginLeft: "10px" }}
              >
                🎯 Final Inspection Done
              </button>
            )}
          </div>
        )}
      </div>

      {toast.message && (
  <Toast
    message={toast.message}
    type={toast.type}
    duration={3000}
    onClose={() => setToast({ message: "", type: "success" })}
  />
)}

    </AdminLayout>
  );
}
