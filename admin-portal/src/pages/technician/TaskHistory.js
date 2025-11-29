import React, { useState, useEffect } from "react";
import axios from "axios";
import API_BASE_URL from "../../apiConfig";
import "./AssignUserTaskPage.css"; // Reuse the same CSS

export default function TaskHistoryPage() {
  const [tasks, setTasks] = useState([]);
  const [message, setMessage] = useState("");

  const technicianId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token || !technicianId) {
      setMessage("❌ User not authenticated");
      return;
    }
    fetchTasks();
    const interval = setInterval(fetchTasks, 10000); // refresh every 10s
    return () => clearInterval(interval);
  }, [token, technicianId]);

  const fetchTasks = async () => {
    if (!token) return;
    try {
      const response = await axios.get(`${API_BASE_URL}/api/tasks/my-tasks`, {
        headers: { Authorization: `Bearer ${token}` },
        params: { technicianId },
      });
      setTasks(response.data);
      setMessage("");
    } catch (error) {
      console.error("Failed to fetch tasks:", error);
      setMessage("❌ Failed to load tasks.");
    }
  };

  // Filter only completed tasks
  const completedTasks = tasks.filter((task) => task.status === "Completed");

  const formatDateArray = (arr) => {
    if (!arr || arr.length < 3) return "N/A";
    const [y, m, d] = arr;
    return new Date(y, m - 1, d).toLocaleDateString("en-IN");
  };

  const handleBack = () => window.history.back();

  return (
    <div className="user-task-container">
      <button className="back-button" onClick={handleBack}>
        ← Back
      </button>
      <h2>📜 Task History</h2>
      {message && <p className="message error">{message}</p>}

      {completedTasks.length === 0 ? (
        <p className="no-task">No completed tasks yet.</p>
      ) : (
        <table className="tasks-table">
          <thead>
            <tr>
              <th>Machine Number</th>
              <th>Start Date</th>
              <th>Target Date</th>
              <th>Completed Date</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {completedTasks.map((task) => (
              <tr key={task.id}>
                <td>{task.machine_number}</td>
                <td>{formatDateArray(task.start_date)}</td>
                <td>{formatDateArray(task.target_date)}</td>
                <td>{formatDateArray(task.completed_date)}</td>
                <td className="status delivered">{task.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
