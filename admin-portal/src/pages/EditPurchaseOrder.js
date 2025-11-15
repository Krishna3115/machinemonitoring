import React, { useEffect, useState } from "react";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import API_BASE_URL from "../apiConfig";
import "./EditPurchaseOrder.css";
import Toast from "../components/Toast";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

export default function EditPurchaseOrder() {
  const [purchaseOrders, setPurchaseOrders] = useState([]);
  const [loading, setLoading] = useState(false);
  const [selectedPO, setSelectedPO] = useState(null);
  const [editData, setEditData] = useState({});
  const [file, setFile] = useState(null);
  const [toastMessage, setToastMessage] = useState("");
  const [toastType, setToastType] = useState("success");

  useEffect(() => {
    fetchPurchaseOrders();
  }, []);

  const fetchPurchaseOrders = async () => {
    try {
      setLoading(true);
      const token = localStorage.getItem("token");
      const res = await axios.get(`${API_BASE_URL}/api/po-orders`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setPurchaseOrders(res.data);
    } catch (error) {
      console.error("Error fetching purchase orders:", error);
      setToastMessage("❌ Failed to load purchase orders.");
      setToastType("error");
    } finally {
      setLoading(false);
    }
  };

  // Convert array date [YYYY,MM,DD] to JS Date
  const arrayToDate = (dateArray) => {
    if (Array.isArray(dateArray) && dateArray.length === 3) {
      return new Date(dateArray[0], dateArray[1] - 1, dateArray[2]);
    }
    return null;
  };

  // Format date for display: dd/MM/yyyy
  const formatDateDisplay = (dateArray) => {
    const d = arrayToDate(dateArray);
    if (!d) return "-";
    return `${String(d.getDate()).padStart(2, "0")}/${String(
      d.getMonth() + 1
    ).padStart(2, "0")}/${d.getFullYear()}`;
  };

  const handleEditClick = (po) => {
    setSelectedPO(po);
    setEditData({
      poNumber: po.po_number,
      finalDispatchDate: arrayToDate(po.finaldispatch_date),
      quantity: po.quantity,
      warrantyMonths: po.warranty_months,
      maintenanceDays: po.maintenance_days,
      erpoa: po.erpoa,
      perDayFine: po.per_day_fine,
    });
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");
      const formPayload = new FormData();

      Object.entries(editData).forEach(([key, value]) => {
        if (key === "finalDispatchDate" && value instanceof Date) {
          // Format date as yyyy-MM-dd for backend
          const formattedDate = `${value.getFullYear()}-${String(
            value.getMonth() + 1
          ).padStart(2, "0")}-${String(value.getDate()).padStart(2, "0")}`;
          formPayload.append(key, formattedDate);
        } else {
          formPayload.append(key, value);
        }
      });

      if (file) formPayload.append("file", file);

      await axios.put(
        `${API_BASE_URL}/api/po-orders/update/${selectedPO.id}`,
        formPayload,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      setToastMessage("✅ Purchase order updated successfully!");
      setToastType("success");
      setSelectedPO(null);
      setFile(null);
      fetchPurchaseOrders();
    } catch (error) {
      console.error("Error updating purchase order:", error);
      setToastMessage("❌ Failed to update purchase order.");
      setToastType("error");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  return (
    <AdminLayout>
      <div className="edit-po-container">
        <h2 className="page-title">📝 Edit Purchase Orders</h2>

        {loading ? (
          <p>Loading purchase orders...</p>
        ) : (
          <div className="table-wrapper">
            <table className="po-table">
              <thead>
                <tr>
                  <th>Sr. No</th>
                  <th>L.O.A Number</th>
                  <th>L.O.A Date</th>
                  <th>Quantity</th>
                  <th>Final Dispatch Date</th>
                  <th>Warranty (Months)</th>
                  <th>Maintenance Days</th>
                  <th>ERP Order</th>
                  <th>Per Day Fine (₹)</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {purchaseOrders.length === 0 ? (
                  <tr>
                    <td colSpan="10">No purchase orders found.</td>
                  </tr>
                ) : (
                  purchaseOrders.map((po, index) => (
                    <tr key={po.id}>
                      <td>{index + 1}</td>
                      <td>{po.po_number}</td>
                      <td>{formatDateDisplay(po.po_date)}</td>
                      <td>{po.quantity}</td>
                      <td>{formatDateDisplay(po.finaldispatch_date)}</td>
                      <td>{po.warranty_months}</td>
                      <td>{po.maintenance_days}</td>
                      <td>{po.erpoa}</td>
                      <td>{po.per_day_fine}</td>
                      <td>
                        <button
                          className="edit-btn"
                          onClick={() => handleEditClick(po)}
                        >
                          ✏️ Edit
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        )}

        {selectedPO && (
          <div className="edit-form-modal">
            <div className="edit-form">
              <div className="edit-form-header">
                <h3>🛠 Edit Purchase Order: {editData.poNumber}</h3>
                <button
                  className="close-btn"
                  onClick={() => setSelectedPO(null)}
                >
                  ✖
                </button>
              </div>

              <form onSubmit={handleUpdate}>
                <div className="form-grid">
                  <div className="form-group">
                    <label className="required">Final Dispatch Date</label>
                    <DatePicker
                      selected={editData.finalDispatchDate}
                      onChange={(date) =>
                        setEditData((prev) => ({
                          ...prev,
                          finalDispatchDate: date,
                        }))
                      }
                      dateFormat="dd/MM/yyyy"
                      className="date-picker-input"
                      required
                    />
                  </div>

                  <div className="form-group">
                    <label className="required">Quantity</label>
                    <input
                      type="number"
                      name="quantity"
                      value={editData.quantity}
                      onChange={handleChange}
                      required
                    />
                  </div>

                  <div className="form-group">
                    <label className="required">Warranty (Months)</label>
                    <input
                      type="number"
                      name="warrantyMonths"
                      value={editData.warrantyMonths}
                      onChange={handleChange}
                      required
                    />
                  </div>

                  <div className="form-group">
                    <label className="required">Maintenance Days</label>
                    <input
                      type="number"
                      name="maintenanceDays"
                      value={editData.maintenanceDays}
                      onChange={handleChange}
                      required
                    />
                  </div>

                  <div className="form-group">
                    <label className="required">ERP Sales Order</label>
                    <input
                      type="text"
                      name="erpoa"
                      value={editData.erpoa}
                      onChange={handleChange}
                      required
                    />
                  </div>

                  <div className="form-group">
                    <label className="required">Per Day Fine (₹)</label>
                    <input
                      type="number"
                      name="perDayFine"
                      value={editData.perDayFine}
                      onChange={handleChange}
                      required
                    />
                  </div>

                  <div className="form-group full">
                    <label>Update Curve File (Optional)</label>
                    <input
                      type="file"
                      accept=".xls,.xlsx"
                      onChange={(e) => setFile(e.target.files[0])}
                    />
                  </div>
                </div>

                <div className="form-actions">
                  <button type="submit" className="save-btn">
                    💾 Save Changes
                  </button>
                  <button
                    type="button"
                    className="cancel-btn"
                    onClick={() => setSelectedPO(null)}
                  >
                    ❌ Cancel
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}

        <Toast
          message={toastMessage}
          type={toastType}
          onClose={() => setToastMessage("")}
        />
      </div>
    </AdminLayout>
  );
}
