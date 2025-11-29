import React, { useState } from "react";
import axios from "axios";
import "./PurchaseOA.css";
import AdminLayout from "../components/AdminLayout";
import API_BASE_URL from "../apiConfig";
import Toast from "../components/Toast";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { format } from "date-fns";

const PurchaseOA = () => {
  const [formData, setFormData] = useState({
    poNumber: "",
    poDate: "",
    finalDispatchDate: "",
    quantity: "",
    warrantyMonths: "",
    maintenanceDays: "",
    erpoa: "",
    perDayFine: "",
    division: "",   // new field
    section: "",    // new field

  });

  const [file, setFile] = useState(null);
  const [contacts, setContacts] = useState([]);
  const [newContact, setNewContact] = useState({
    name: "",
    designation: "",
    mobile: "",
    division: "",
    section: "",
  });
  const [showContactForm, setShowContactForm] = useState(false);
  const [toastMessage, setToastMessage] = useState("");
  const [toastType, setToastType] = useState("success");

  // Date formatting helpers
  const backendDate = (date) => (date ? format(date, "yyyy-MM-dd") : "");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");

    const formPayload = new FormData();
    Object.entries(formData).forEach(([key, value]) =>
      formPayload.append(key, value)
    );
    if (file) formPayload.append("file", file);
    formPayload.append("contacts", JSON.stringify(contacts));

    try {
      await axios.post(`${API_BASE_URL}/api/po-orders/upload`, formPayload, {
        headers: { Authorization: `Bearer ${token}` },
      });

      setToastMessage("✅ PO and curve details uploaded successfully!");
      setToastType("success");

      // Reset form
      setFormData({
        poNumber: "",
        poDate: "",
        dispatchDate: "",
        quantity: "",
        warrantyMonths: "",
        maintenanceDays: "",
        erpoa: "",
        perDayFine: "",
        division: "",
        section: "",
      });
      setFile(null);
      setContacts([]);
    } catch (err) {
      console.error("Error uploading PO and curve details:", err);
      setToastMessage("❌ Failed to upload PO. Please try again.");
      setToastType("error");
    }
  };

  return (
    <AdminLayout>
      <div className="purchase-container">
        <div className="purchase-card">
          <h2 className="page-title">📄 Add New Purchase Order</h2>
          <p className="subtitle">
            All fields are <strong>required</strong> except curve file.
          </p>

          <form className="purchase-form" onSubmit={handleSubmit}>
            <div className="form-grid">
              {/* L.O.A Number */}
              <div className="form-group">
                <label className="required">L.O.A Number</label>
                <input
                  name="poNumber"
                  type="text"
                  value={formData.poNumber}
                  onChange={handleChange}
                  required
                  placeholder="Enter L.O.A Number"
                />
              </div>

              {/* L.O.A Date */}
              <div className="form-group">
                <label className="required">L.O.A Date</label>
                <DatePicker
                  selected={
                    formData.poDate ? new Date(formData.poDate) : null
                  }
                  onChange={(date) =>
                    setFormData({ ...formData, poDate: backendDate(date) })
                  }
                  dateFormat="dd/MM/yyyy"
                  placeholderText="Select Date"
                  showMonthDropdown
                  showYearDropdown
                  dropdownMode="select"
                  className="styled-datepicker"
                  required
                />
              </div>

              {/* Final Dispatch Date */}
              <div className="form-group">
                <label className="required">Final Dispatch Date</label>
                <DatePicker
                  selected={
                    formData.dispatchDate ? new Date(formData.dispatchDate) : null
                  }
                  onChange={(date) =>
                    setFormData({
                      ...formData,
                      dispatchDate: backendDate(date),
                    })
                  }
                  dateFormat="dd/MM/yyyy"
                  placeholderText="Select Date"
                  showMonthDropdown
                  showYearDropdown
                  dropdownMode="select"
                  className="styled-datepicker"
                  required
                />
              </div>

              {/* Quantity */}
              <div className="form-group">
                <label className="required">Quantity</label>
                <input
                  name="quantity"
                  type="number"
                  inputMode="numeric"
                  pattern="[0-9]*"
                  value={formData.quantity}
                  onChange={handleChange}
                  required
                  placeholder="Enter Quantity"
                />
              </div>

              {/* Warranty Months */}
              <div className="form-group">
                <label className="required">Warranty (Months)</label>
                <input
                  name="warrantyMonths"
                  type="number"
                  inputMode="numeric"
                  pattern="[0-9]*"
                  value={formData.warrantyMonths}
                  onChange={handleChange}
                  required
                  placeholder="Enter Warranty"
                />
              </div>

              {/* Maintenance Days */}
              <div className="form-group">
                <label className="required">Maintenance Days</label>
                <input
                  name="maintenanceDays"
                  type="number"
                  inputMode="numeric"
                  pattern="[0-9]*"
                  value={formData.maintenanceDays}
                  onChange={handleChange}
                  required
                  placeholder="Enter Days"
                />
              </div>

              {/* ERP Sales Order */}
              <div className="form-group">
                <label className="required">ERP Sales Order</label>
                <input
                  name="erpoa"
                  type="text"
                  value={formData.erpoa}
                  onChange={handleChange}
                  required
                  placeholder="Enter ERP Number"
                />
              </div>

              {/* Per Day Fine */}
              <div className="form-group">
                <label className="required">Per Day Fine (₹)</label>
                <input
                  name="perDayFine"
                  type="number"
                  inputMode="numeric"
                  pattern="[0-9]*"
                  value={formData.perDayFine}
                  onChange={handleChange}
                  required
                  placeholder="Enter Fine"
                />
              </div>

              {/* Division */}
              <div className="form-group">
                <label className="required">Division</label>
                <input
                  name="division"
                  type="text"
                  value={formData.division}
                  onChange={handleChange}
                  required
                  placeholder="Enter Division"
                />
              </div>

              {/* Section */}
              <div className="form-group">
                <label className="required">Section</label>
                <input
                  name="section"
                  type="text"
                  value={formData.section}
                  onChange={handleChange}
                  required
                  placeholder="Enter Section"
                />
              </div>


              {/* File Upload */}
              <div className="form-group full">
                <label>Upload Curve Details Excel (Optional)</label>
                <input
                  type="file"
                  accept=".xls,.xlsx"
                  onChange={(e) => setFile(e.target.files[0])}
                />
              </div>

              {/* Contacts Section */}
              <div className="form-group full">
                <label>Consignee Contacts</label>
                <button
                  type="button"
                  className="btn-primary"
                  onClick={() => setShowContactForm(true)}
                >
                  ➕ Add Contact
                </button>

                {contacts.length > 0 && (
                  <ul className="contact-list">
                    {contacts.map((c, i) => (
                      <li key={i}>
                        <strong>{i + 1}.</strong> {c.name} – {c.designation} –{" "}
                        {c.mobile} – {c.division} – {c.section}
                      </li>
                    ))}
                  </ul>
                )}
              </div>
            </div>

            <div className="form-actions">
              <button type="submit" className="btn-primary">
                💾 Save Purchase Order
              </button>
            </div>
          </form>
        </div>
      </div>

      {/* 🧩 Contact Modal */}
      {showContactForm && (
        <div className="contact-form-modal">
          <div className="contact-card">
            <h4>Add Consignee Contact</h4>

            <div className="form-grid">
              <div className="form-group">
                <label>Name</label>
                <input
                  type="text"
                  value={newContact.name}
                  onChange={(e) =>
                    setNewContact({ ...newContact, name: e.target.value })
                  }
                  required
                />
              </div>
              <div className="form-group">
                <label>Designation</label>
                <input
                  type="text"
                  value={newContact.designation}
                  onChange={(e) =>
                    setNewContact({ ...newContact, designation: e.target.value })
                  }
                  required
                />
              </div>
              <div className="form-group">
                <label>Mobile No.</label>
                <input
                  type="text"
                  value={newContact.mobile}
                  onChange={(e) => {
                    const val = e.target.value;
                    if (/^\d{0,10}$/.test(val)) {
                      setNewContact({ ...newContact, mobile: val });
                    }
                  }}
                  placeholder="Enter 10-digit number"
                  required
                />
              </div>
              <div className="form-group">
                <label>Division</label>
                <input
                  type="text"
                  value={newContact.division}
                  onChange={(e) =>
                    setNewContact({ ...newContact, division: e.target.value })
                  }
                  required
                />
              </div>
              <div className="form-group">
                <label>Section</label>
                <input
                  type="text"
                  value={newContact.section}
                  onChange={(e) =>
                    setNewContact({ ...newContact, section: e.target.value })
                  }
                  required
                />
              </div>
            </div>

            <div className="form-actions">
              <button
                className="btn-primary"
                type="button"
                onClick={() => {
                  setContacts([...contacts, newContact]);
                  setNewContact({
                    name: "",
                    designation: "",
                    mobile: "",
                    division: "",
                    section: "",
                  });
                  setShowContactForm(false);
                }}
              >
                ✅ Add Contact
              </button>
              <button
                className="btn-secondary"
                type="button"
                onClick={() => setShowContactForm(false)}
              >
                ❌ Cancel
              </button>
            </div>
          </div>
        </div>
      )}

      <Toast
        message={toastMessage}
        type={toastType}
        onClose={() => setToastMessage("")}
      />
    </AdminLayout>
  );
};

export default PurchaseOA;
