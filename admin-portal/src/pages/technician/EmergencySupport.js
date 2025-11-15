import React, { useState } from "react";
import "./EmergencySupport.css";

export default function EmergencySupport() {
  const [message, setMessage] = useState("");

  const triggerEmergency = async (type) => {
    try {
      // Replace this with your real API endpoint
      await fetch("/api/emergency-alerts", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          type,
          location: "Auto GPS placeholder",
          timestamp: new Date().toISOString(),
        }),
      });
      setMessage(`${type} alert sent successfully.`);
    } catch (err) {
      setMessage(`Failed to send ${type} alert.`);
    }
  };

  return (
    <div className="emergency-page">
      <h1>🚨 Emergency Support Center</h1>
      <p className="sub-text">Choose the type of emergency to report. Help will be dispatched immediately.</p>

      <div className="card-container">
        {/* Medical Emergency Card */}
        <div className="emergency-card red">
          <h2>🚑 Medical Emergency</h2>
          <p>If injured or facing health issues, click below.</p>
          <button onClick={() => triggerEmergency("Medical")}>Send Medical SOS</button>
        </div>

        {/* Personal Safety Emergency */}
        <div className="emergency-card yellow">
          <h2>🛡️ Protection Emergency</h2>
          <p>If you feel unsafe or threatened, alert security now.</p>
          <button onClick={() => triggerEmergency("Protection")}>Trigger Panic Alert</button>
        </div>

        {/* Technical Emergency */}
        <div className="emergency-card blue">
          <h2>📞 Technical Emergency</h2>
          <p>Having a serious technical or machine failure?</p>
          <button onClick={() => triggerEmergency("Technical")}>Request Urgent Support</button>
        </div>
      </div>

      {message && <p className="alert-message">{message}</p>}
    </div>
  );
}
