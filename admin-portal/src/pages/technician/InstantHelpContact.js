import React from "react";
import { useNavigate } from "react-router-dom";
import "./InstantHelpContact.css";

const helpContacts = [
  {
    id: 1,
    name: "Rajesh Kumar",
    mobileNumber: "9876543210",
    summary: "Site Head – North Zone"
  },
  {
    id: 2,
    name: "Sunil Verma",
    mobileNumber: "9123456789",
    summary: "Supervisor – Section A"
  },
  {
    id: 3,
    name: "Pooja Mehra",
    mobileNumber: "8901234567",
    summary: "Engineer – Zone B"
  }
];

const InstantHelpContact = () => {
  const navigate = useNavigate();

  const handleBack = () => {
    navigate(-1); // goes back to previous page
  };

  return (
    <div className="instant-help-container">
      <button className="back-button" onClick={handleBack}>
        ← Back
      </button>
      <h2>Instant Help Contacts</h2>
      <ul className="contact-list">
        {helpContacts.map((c) => (
          <li key={c.id} className="contact-item">
            <strong>{c.name}</strong>
            <p>
              📞 <a href={`tel:${c.mobileNumber}`}>{c.mobileNumber}</a>
            </p>
            {c.summary && <small>{c.summary}</small>}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default InstantHelpContact;
