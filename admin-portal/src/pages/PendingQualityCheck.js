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

  const today = new Date();
  const formattedDate = today.toLocaleDateString("en-GB");

  // ✅ Group selected machines by batch (jobCardNo)
  const groupedByBatch = selectedMachines.reduce((acc, machine) => {
    const batch = machine.jobCardNo;
    if (!acc[batch]) acc[batch] = [];
    acc[batch].push(machine.machineSerialNo);
    return acc;
  }, {});

  const styles = `
    <style>
      @page { size: A4 landscape; margin: 15mm 12mm 15mm 12mm; }
      body { font-family: Arial, sans-serif; margin: 0; padding: 0; font-size: 13px; }

      .page-border {
        border: 2px solid #000;
        padding: 15px;
        margin: 12px;
        box-sizing: border-box;
        page-break-after: always;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
      }

      .header { text-align: center; margin-bottom: 10px; }
      .header h1 { margin: 0; font-size: 32px; }
      .header h2 { margin: 4px 0; font-size: 22px; }

      .info-container {
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 14px;
        margin-bottom: 15px;
      }

      .info-box {
        flex: 1;
        border: 1px solid #000;
        padding: 4px 6px;
        font-size: 13px;
        box-sizing: border-box;
      }

      .info-box.left { text-align: left; }
      .info-box.center { text-align: center; margin: 0 6px; }

      table {
        width: 100%;
        border-collapse: collapse;
        table-layout: fixed;
        word-wrap: break-word;
      }

      th, td {
        border: 1px solid #000;
        padding: 4px;
        text-align: center;
        vertical-align: middle;
      }

      th {
        background-color: #f2f2f2;
        font-weight: bold;
      }

      th:nth-child(1), td:nth-child(1) { width: 40px; }
      th:nth-child(2), td:nth-child(2) { width: 160px; }
      th:nth-child(3), td:nth-child(3) { width: 75px; }
      th:nth-child(4), td:nth-child(4) { width: 55px; }
      th:nth-child(5), td:nth-child(5) { width: 55px; }
      th:nth-child(6), td:nth-child(6) { width: 170px; }
      th.machine-col, td.machine-col { width: auto; }

      .below-table {
        margin-top: 16px;
        display: flex;
        justify-content: space-between;
        font-size: 13px;
      }

      .remarks { margin-top: 12px; font-size: 13px; }

      .signatures {
        margin-top: 40px;
        display: flex;
        justify-content: space-between;
        font-size: 13px;
      }

      .signatures div {
        width: 45%;
        text-align: center;
        padding-top: 8px;
      }

      .format-info {
        margin-top: 8px;
        text-align: center;
        font-size: 11px;
      }
    </style>
  `;

  const inspectionRows = [
    ["1", "Grease volume @3 sec. cycle time", "25-30", "gms", "+/-5", "Weigh. Scale & Stopwatch"],
    ["2", "Solar Voltage", "26-38", "DC Volt", "+/-2", "Multimeter"],
    ["3", "Battery Voltage", "27-29", "DC Volt", "+/-2", "Multimeter"],
    ["4", "Motor Direction during Rotation", "Clockwise keeping motor at left side", "", "", "Visual Check"],
    ["5", "Sensor", "Sensor LED indicator / activation signal sent to controller", "", "", "Visual Check"],
    ["6", "MCB", "Should Trip", "", "", "Manual trip test"],
    ["7", "Batch Counter", "Should reset to zero when triggered", "", "", "Visual Check"],
    ["8", "Charge Controller", "26-29 V", "", "", "Multimeter"],
    ["9", "Cabinet", "Check for external damage, door alignment, mounting integrity", "", "", "Visual Check"],
    ["10", "Applicator", "Mounting Bolts, Clamps, Brackets & fitment on rail", "", "", "Check fitment"],
  ];

  let pages = "";

  for (const [batchNo, serials] of Object.entries(groupedByBatch)) {
    const chunks = [];
    for (let i = 0; i < serials.length; i += 5) {
      chunks.push(serials.slice(i, i + 5));
    }

    chunks.forEach((serialGroup) => {
      const [m1, m2, m3, m4, m5] = serialGroup;

      const machineHeaderCols = `
        ${m1 ? `<th class="machine-col">${m1}</th>` : ""}
        ${m2 ? `<th class="machine-col">${m2}</th>` : ""}
        ${m3 ? `<th class="machine-col">${m3}</th>` : ""}
        ${m4 ? `<th class="machine-col">${m4}</th>` : ""}
        ${m5 ? `<th class="machine-col">${m5}</th>` : ""}
      `;

      const tableRows = inspectionRows
        .map((row, idx) => {
          // ✅ Merge Spec+Units+Tol. for rows after 3rd
          if (idx < 3) {
            return `
              <tr>
                <td>${row[0]}</td>
                <td>${row[1]}</td>
                <td>${row[2]}</td>
                <td>${row[3]}</td>
                <td>${row[4]}</td>
                <td>${row[5]}</td>
                <td class="machine-col"></td>
                <td class="machine-col"></td>
                <td class="machine-col"></td>
                <td class="machine-col"></td>
                <td class="machine-col"></td>
              </tr>`;
          } else {
            return `
              <tr>
                <td>${row[0]}</td>
                <td>${row[1]}</td>
                <td colspan="3" style="text-align:center;">${row[2]}</td>
                <td>${row[5]}</td>
                <td class="machine-col"></td>
                <td class="machine-col"></td>
                <td class="machine-col"></td>
                <td class="machine-col"></td>
                <td class="machine-col"></td>
              </tr>`;
          }
        })
        .join("");

      pages += `
        <div class="page-border">
          <div>
            <div class="header">
              <h1>CHAKRADHAR INDUSTRIES LLP</h1>
              <h2>FINAL INSPECTION REPORT – TBL</h2>
            </div>

            <div class="info-container">
              <div class="info-box left"><strong>Batch Card No.:</strong> ${batchNo}</div>
              <div class="info-box center"><strong>Issue Date:</strong> ${formattedDate}</div>
              <div class="info-box left"><strong>Inspection Date:</strong> </div>
            </div>

            <table>
              <thead>
                <tr>
                  <th>Sr No.</th>
                  <th>Observation Details</th>
                  <th>Spec</th>
                  <th>Units</th>
                  <th>Tol.</th>
                  <th>Evaluation Meas. Tech.</th>
                  ${machineHeaderCols}
                </tr>
              </thead>
              <tbody>${tableRows}</tbody>
            </table>

            <div class="below-table">
              <div><strong>All test passed:</strong> YES / NO</div>
              <div><strong>Issue noted:</strong> YES / NO</div>
            </div>

            <div class="remarks"><strong>Remarks:</strong> </div>
          </div>

          <div>
            <div class="signatures">
              <div>Checked By</div>
              <div>Approved By</div>
            </div>

            <div class="format-info">
              Format No. CIL/TBL-FIR/004 &nbsp;&nbsp; Rev: 05 &nbsp;&nbsp; Issue Date: 05-08-2025
            </div>
          </div>
        </div>
      `;
    });
  }

  const content = `
    <html>
    <head><title>Final Inspection Report</title>${styles}</head>
    <body>
      ${pages}
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
              🖨️ Print Inspection Report
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
                📤 Upload QC Report
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
