import React from "react";
import { useNavigate } from "react-router-dom";
import "./InstantHelpContact.css";

const helpContacts = [
  {
    id: 1,
    name: "kunal Bhoir",
    mobileNumber: "8983630991",
    summary: ""
  },
  {
    id: 2,
    name: "Satish Kadali",
    mobileNumber: "9167266242",
    summary: "Supervisor – Section A"
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
