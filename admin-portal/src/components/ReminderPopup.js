import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./ReminderPopup.css";

// Set the reminder interval here
const oneHour = 1000 * 10;

//const oneHour = 1000 * 60 * 60;
const ReminderPopup = () => {
  const [reminders, setReminders] = useState([]);
  const [current, setCurrent] = useState(null);
  const navigate = useNavigate();

    useEffect(() => {
    const checkReminders = () => {
        const now = Date.now();
        const list = JSON.parse(localStorage.getItem("reminderList") || "[]");

    const due = list.filter(
        (item) =>
          (!item.lastDismissed || item.lastDismissed < item.timestamp)
      );
      
        console.log("Reminder list:", list);
        console.log("Due reminders:", due);

        setReminders(due);
        setCurrent(due[0] || null);
    };

    const interval = setInterval(checkReminders, 5000);
    checkReminders();
    return () => clearInterval(interval);
    }, []);

  const handleDismiss = () => {
    const updatedList = JSON.parse(localStorage.getItem("reminderList") || "[]").map((item) =>
      item.id === current.id && item.type === current.type
        ? { ...item, lastDismissed: Date.now() }
        : item
    );

    localStorage.setItem("reminderList", JSON.stringify(updatedList));
    const next = reminders.slice(1);
    setReminders(next);
    setCurrent(next[0] || null);
  };

  const handleGoNow = () => {
    switch (current.type) {
      case "pending-delivery":
        navigate("/admin/pending-deliveries");
        break;
      case "insurance":
        navigate("/admin/insurance/process-list");
        break;
      case "vandalism":
        navigate("/admin/vandalism-report");
        break;
      default:
        break;
    }
    handleDismiss();
  };

  if (!current) return null;

  return (
    <div className="reminder-popup-overlay">
      <div className="reminder-popup-box">
        <h3>Reminder</h3>
        <p>{current.message}</p>
        <div className="reminder-popup-buttons">
          <button onClick={handleGoNow}>Go Now</button>
          <button onClick={handleDismiss}>Do It Later</button>
        </div>
      </div>
    </div>
  );
};

export default ReminderPopup;
