import React, { useState, useEffect } from "react";
import axios from "axios";
import API_BASE_URL from "../../apiConfig";
import "./AssignUserTaskPage.css";

export default function AssignUserTaskPage() {
  const [tasks, setTasks] = useState([]);
  const [message, setMessage] = useState("");

  const technicianId = localStorage.getItem("userId");

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const token = localStorage.getItem("token");

        const response = await axios.get(`${API_BASE_URL}/api/tasks/my-tasks`, {
          headers: { Authorization: `Bearer ${token}` },
          params: { technicianId }
        });

        setTasks(response.data);
      } catch (error) {
        console.error("Failed to fetch tasks:", error);
        setMessage("❌ Failed to load tasks.");
      }
    };

    fetchTasks();
  }, [technicianId]);

  // Convert [YYYY, MM, DD] → JS Date
  const formatArrayDate = (arr) => {
    if (!arr || arr.length < 3) return "N/A";
    const [y, m, d] = arr;
    const dateObj = new Date(y, m - 1, d);
    return dateObj.toLocaleDateString("en-IN");
  };

  const handleBack = () => window.history.back();

  return (
    <div className="user-task-container">

      <button className="back-button" onClick={handleBack}>← Back</button>
      <h2>📝 My Assigned Tasks</h2>

      {message && <p className="message error">{message}</p>}

      {tasks.length === 0 ? (
        <p>No tasks assigned yet.</p>
      ) : (
        <table className="tasks-table">
          <thead>
            <tr>
              <th>Machine Number</th>
              <th>Task Type</th>
              <th>Schedule Date</th>
              <th>Status</th>
            </tr>
          </thead>

          <tbody>
            {tasks.map((task) => (
              <tr key={task.id}>
                <td>{task.machine_number}</td>
                <td>{task.task_type}</td>
                <td>{formatArrayDate(task.schedule_date)}</td>
                <td>{task.status || "Pending"}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
