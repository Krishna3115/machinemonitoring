import React, { useEffect, useState } from "react";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import "./AvailableMachineDispatch.css";
import API_BASE_URL from "../apiConfig";

// Convert date array like [2025,9,16,20,2,7] into JS Date object
const dateArrayToDate = (arr) => {
  if (!arr || !arr.length) return null;
  const [year, month, day, hour = 0, minute = 0, second = 0] = arr;
  return new Date(year, month - 1, day, hour, minute, second);
};

// Format JS Date object into DD-MM-YYYY
const formatDate = (date) => {
  if (!date) return "–";
  return `${date.getDate().toString().padStart(2, "0")}-${(date.getMonth() + 1)
    .toString()
    .padStart(2, "0")}-${date.getFullYear()}`;
};

// Calculate days ago from JS Date object
const calculateDaysAgo = (date) => {
  if (!date) return null;
  return Math.floor((new Date() - date) / (1000 * 60 * 60 * 24));
};

export default function AvailableForDispatch() {
  const [machines, setMachines] = useState([]);
  const [selectedMachines, setSelectedMachines] = useState(new Set());
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchAvailableMachines();
  }, []);

  const fetchAvailableMachines = async () => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get(
        `${API_BASE_URL}/api/machines-production/ready-to-dispatch`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setMachines(res.data);
    } catch (err) {
      console.error("Failed to fetch available machines:", err);
      alert("Failed to load machines. Check console.");
    }
  };

  const toggleSelection = (serialNo) => {
    const newSelection = new Set(selectedMachines);
    if (newSelection.has(serialNo)) {
      newSelection.delete(serialNo);
    } else {
      newSelection.add(serialNo);
    }
    setSelectedMachines(newSelection);
  };

  const handleGenerateQRClick = async () => {
    if (selectedMachines.size === 0) {
      alert("Please select at least one machine to generate QR code.");
      return;
    }

    setLoading(true);

    try {
      const token = localStorage.getItem("token");

      const qrPromises = Array.from(selectedMachines).map(async (serialNo) => {
        const res = await axios.get(
          `${API_BASE_URL}/api/machines-production/${serialNo}/qrcode`,
          {
            headers: { Authorization: `Bearer ${token}` },
            responseType: "blob",
          }
        );

        const blob = res.data;
        const imageBitmap = await createImageBitmap(blob);

        // Create canvas to add serial number above QR
        const canvas = document.createElement("canvas");
        const ctx = canvas.getContext("2d");
        const padding = 30; // space for text
        canvas.width = imageBitmap.width;
        canvas.height = imageBitmap.height + padding;

        // Fill background white
        ctx.fillStyle = "white";
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        // Draw the serial number text
        ctx.fillStyle = "black";
        ctx.font = "bold 20px Arial";
        ctx.textAlign = "center";
        ctx.fillText(serialNo, canvas.width / 2, 20);

        // Draw the QR image below the text
        ctx.drawImage(imageBitmap, 0, padding);

        // Convert canvas to blob for download
        return new Promise((resolve) => {
          canvas.toBlob((finalBlob) => {
            resolve({ serialNo, blob: finalBlob });
          });
        });
      });

      const qrResults = await Promise.all(qrPromises);

      qrResults.forEach(({ serialNo, blob }) => {
        const url = URL.createObjectURL(blob);
        const link = document.createElement("a");
        link.href = url;
        link.download = `${serialNo}_QR.png`;
        link.click();
        URL.revokeObjectURL(url);
      });

      alert(`${qrResults.length} QR code(s) generated successfully!`);
      setSelectedMachines(new Set());
    } catch (err) {
      console.error("Failed to generate QR codes:", err);
      alert("Failed to generate some QR codes. Check console for details.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <AdminLayout>
      <div className="technician-list-container">
        <h2>Machines Ready for Dispatch</h2>

        {machines.length > 0 && (
          <button
            disabled={selectedMachines.size === 0 || loading}
            onClick={handleGenerateQRClick}
            style={{ marginBottom: "10px" }}
          >
            {loading ? "Generating QR(s)..." : "Generate QR(s)"}
          </button>
        )}

        <table className="technician-table">
          <thead>
            <tr>
              <th>Select</th>
              <th>Machine Serial No</th>
              <th>Status</th>
              <th>Final Inspection Date</th>
              <th>Days From Final QA</th>
            </tr>
          </thead>
          <tbody>
            {machines.length === 0 ? (
              <tr>
                <td colSpan="5" style={{ textAlign: "center" }}>
                  No machines ready for dispatch.
                </td>
              </tr>
            ) : (
              machines.map((m) => {
                const qcDate = dateArrayToDate(m.qc_inspection_date);
                const formattedDate = formatDate(qcDate);
                const daysAgo = calculateDaysAgo(qcDate);

                return (
                  <tr key={m.machine_serial_no}>
                    <td>
                      <input
                        type="checkbox"
                        checked={selectedMachines.has(m.machine_serial_no)}
                        onChange={() => toggleSelection(m.machine_serial_no)}
                      />
                    </td>
                    <td>{m.machine_serial_no}</td>
                    <td>{m.status || "–"}</td>
                    <td>{formattedDate}</td>
                    <td>
                      {daysAgo !== null
                        ? `${daysAgo} day${daysAgo !== 1 ? "s" : ""} ago`
                        : "–"}
                    </td>
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
