import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
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
  const navigate = useNavigate();

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

  const handleGenerateQRClick = () => {
    if (selectedMachines.size === 1) {
      const [serialNo] = Array.from(selectedMachines);
      navigate(`/admin/${serialNo}/qrcode`);
    } else {
      alert("Please select exactly one machine to generate QR code.");
    }
  };

  return (
    <AdminLayout>
      <div className="technician-list-container">
        <h2>Machines Ready for Dispatch</h2>

        {machines.length > 0 && (
          <button
            disabled={selectedMachines.size !== 1}
            onClick={handleGenerateQRClick}
            style={{ marginBottom: "10px" }}
          >
            Generate QR
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
