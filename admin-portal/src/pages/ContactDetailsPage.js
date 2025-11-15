import React, { useEffect, useState } from "react";
import axios from "axios";
import AdminLayout from "../components/AdminLayout";
import { FaPhone } from "react-icons/fa";
import API_BASE_URL from "../apiConfig";
import "./ContactDetailsPage.css";

export default function ContactDetailsPage() {
  const [loaContacts, setLoaContacts] = useState([]);
  const [filters, setFilters] = useState({ poNumber: "", division: "", section: "" });
  const [modalData, setModalData] = useState([]);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    fetchContactDetails();
  }, []);

  const fetchContactDetails = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get(`${API_BASE_URL}/api/po-orders/contact-details`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setLoaContacts(response.data);
    } catch (err) {
      console.error("Error fetching contact details:", err);
    }
  };

  const handleFilterChange = (e) => {
    const updatedFilters = { ...filters, [e.target.name]: e.target.value };
    setFilters(updatedFilters);
  };

  const filteredContacts = loaContacts.filter((entry) =>
    (!filters.poNumber || entry.poNumber.includes(filters.poNumber)) &&
    (!filters.division || (entry.division || "").toLowerCase().includes(filters.division.toLowerCase())) &&
    (!filters.section || (entry.section || "").toLowerCase().includes(filters.section.toLowerCase()))
  );

  const onCallLog = (contacts) => {
    setModalData(contacts || []);
    setShowModal(true);
  };

  return (
    <AdminLayout>
      <div className="contact-details-container">
        <h2>📞 Contact Book by L.O.A</h2>

        <div className="filters">
          <input
            type="text"
            name="poNumber"
            placeholder="L.O.A No"
            value={filters.poNumber}
            onChange={handleFilterChange}
          />
          <input
            type="text"
            name="division"
            placeholder="Division"
            value={filters.division}
            onChange={handleFilterChange}
          />
          <input
            type="text"
            name="section"
            placeholder="Section"
            value={filters.section}
            onChange={handleFilterChange}
          />
        </div>

        <table className="contact-table">
          <thead>
            <tr>
              <th>L.O.A No</th>
              <th>Division</th>
              <th>Section</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredContacts.length === 0 ? (
              <tr>
                <td colSpan="4" style={{ textAlign: "center" }}>No records found.</td>
              </tr>
            ) : (
              filteredContacts.map((entry, index) => (
                <tr key={index}>
                  <td>{entry.poNumber}</td>
                  <td>{entry.division || "-"}</td>
                  <td>{entry.section || "-"}</td>
                  <td>
                    <FaPhone
                      className="icon"
                      title="Show all contacts"
                      style={{ cursor: "pointer" }}
                      onClick={() => onCallLog(entry.consigneeContactList)}
                    />
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>

        {showModal && (
          <div className="modal-overlay" onClick={() => setShowModal(false)}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
              <h3>📱 Contact Details</h3>
              <table className="modal-table">
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Designation</th>
                    <th>Mobile</th>
                  </tr>
                </thead>
                <tbody>
                  {modalData.length === 0 ? (
                    <tr>
                      <td colSpan="3">No contacts available</td>
                    </tr>
                  ) : (
                    modalData.map((contact, i) => (
                      <tr key={i}>
                        <td>{contact.name || "-"}</td>
                        <td>{contact.designation || "-"}</td>
                        <td>{contact.mobile || "-"}</td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
              <button onClick={() => setShowModal(false)}>Close</button>
            </div>
          </div>
        )}
      </div>
    </AdminLayout>
  );
}
