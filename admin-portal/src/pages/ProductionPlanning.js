import React, { useEffect, useState } from "react";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import API_BASE_URL from "../apiConfig";
import "./ProductionPlanning.css";
import Toast from "../components/Toast";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { format } from "date-fns";

export default function ProductionPlanningPage() {
  const [loaList, setLoaList] = useState([]);
  const [selectedLOA, setSelectedLOA] = useState("");
  const [remainingQuantity, setRemainingQuantity] = useState(0);
  const [plannedQuantity, setPlannedQuantity] = useState("");
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [toastMessage, setToastMessage] = useState("");
  const [toastType, setToastType] = useState(""); // 'success' or 'error'
  const [loading, setLoading] = useState(false);
  const [selectedDivision, setSelectedDivision] = useState("");
  const [selectedSection, setSelectedSection] = useState("");
  const [selectedDispatchDate, setSelectedDispatchDate] = useState("");


  useEffect(() => {
    fetchLOAs();
  }, []);

  const fetchLOAs = async () => {
    setLoading(true);
    try {
      const res = await axios.get(`${API_BASE_URL}/api/production-planning/available-loas`, {
        withCredentials: true,
      });

      if (Array.isArray(res.data)) {
        setLoaList(res.data);
      } else {
        setLoaList([]);
        setToastMessage("⚠️ Unexpected API response format.");
        setToastType("error");
      }
    } catch (err) {
      console.error("❌ Failed to load LOAs:", err);
      setToastMessage("Failed to load LOA data from the server.");
      setToastType("error");
    } finally {
      setLoading(false);
    }
  };

  const handleLOAChange = (e) => {
    const poNumber = e.target.value;
    setSelectedLOA(poNumber);
    const selected = loaList.find((item) => item.po_number === poNumber);
    setRemainingQuantity(selected ? selected.remaining_quantity : 0);
    setSelectedDivision(selected ? selected.division : "");
    setSelectedSection(selected ? selected.section : "");

    const dispatchDate = selected?.final_dispatch_date
      ? new Date(selected.final_dispatch_date).toLocaleDateString("en-GB")
      : "";
    setSelectedDispatchDate(dispatchDate);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!selectedLOA || !plannedQuantity || !startDate || !endDate) {
      setToastMessage("⚠️ Please fill in all required fields.");
      setToastType("error");
      return;
    }

    if (parseInt(plannedQuantity, 10) > remainingQuantity) {
      setToastMessage("❌ Planned quantity exceeds available quantity.");
      setToastType("error");
      return;
    }

    const dto = {
      poNumber: selectedLOA,
      plannedQuantity: parseInt(plannedQuantity, 10),
      startDate: format(startDate, "yyyy-MM-dd"),
      endDate: format(endDate, "yyyy-MM-dd"),
    };

    try {
      await axios.post(`${API_BASE_URL}/api/production-planning`, dto, {
        withCredentials: true,
      });

      setToastMessage("✅ Production plan created successfully.");
      setToastType("success");

      // Reset form
      setPlannedQuantity("");
      setStartDate(null);
      setEndDate(null);
      setSelectedLOA("");

      fetchLOAs();
    } catch (err) {
      console.error("❌ Error saving production plan:", err);
      const msg = err.response?.data || "Server error. Please try again.";
      setToastMessage(typeof msg === "string" ? msg : "Server error.");
      setToastType("error");
    }
  };

  return (
    <AdminLayout>
      <div className="planning-container">
        <h2>📅 Production Planning</h2>

        {loading ? (
          <p>Loading available LOAs...</p>
        ) : (
          <form onSubmit={handleSubmit} className="planning-form">
            <div className="form-group">
              <label>Sales Order (SO) Number:</label>
              <select value={selectedLOA} onChange={handleLOAChange} required>
                <option value="">-- Select Sales Order --</option>
                {loaList.map((loa, index) => {
                  const formattedDate = loa.final_dispatch_date
                    ? new Date(loa.final_dispatch_date).toLocaleDateString("en-GB") // dd/MM/yyyy
                    : "";
                  return (
                    <option key={index} value={loa.po_number}>
                      {`${loa.po_number} | ${formattedDate} | ${loa.division || ""} | ${loa.section || ""}`}
                    </option>
                  );
                })}
              </select>


            </div>

            <div className="form-group">
              <label>Total Sales Order Quantity:</label>
              <input
                type="number"
                value={
                  loaList.find((l) => l.po_number === selectedLOA)?.total_quantity || 0
                }
                readOnly
              />
            </div>

            <div className="form-group">
              <label>Remaining Quantity:</label>
              <input type="number" value={remainingQuantity} readOnly />
            </div>

            <div className="form-group">
              <label>Planned Quantity:</label>
              <input
                type="number"
                value={plannedQuantity}
                onChange={(e) => setPlannedQuantity(e.target.value)}
                required
                min="1"
              />
            </div>

            <div className="form-group">
              <label>Planned Start Date:</label>
              <DatePicker
                selected={startDate}
                onChange={(date) => setStartDate(date)}
                dateFormat="dd/MM/yyyy"
                placeholderText="DD/MM/YYYY"
                className="date-picker-input"
                required
              />
            </div>

            <div className="form-group">
              <label>Target End Date:</label>
              <DatePicker
                selected={endDate}
                onChange={(date) => setEndDate(date)}
                dateFormat="dd/MM/yyyy"
                placeholderText="DD/MM/YYYY"
                className="date-picker-input"
                required
              />
            </div>

            <div className="form-group">
              <label>Division:</label>
              <input type="text" value={selectedDivision} readOnly />
            </div>

            <div className="form-group">
              <label>Section:</label>
              <input type="text" value={selectedSection} readOnly />
            </div>

            <div className="form-group">
              <label>Target Dispatch Date:</label>
              <input type="text" value={selectedDispatchDate} readOnly />
            </div>


            <button type="submit" className="submit-button">
              💾 Save Plan
            </button>
          </form>
        )}
      </div>

      <Toast
        message={toastMessage}
        type={toastType}
        onClose={() => setToastMessage("")}
      />
    </AdminLayout>
  );
}
