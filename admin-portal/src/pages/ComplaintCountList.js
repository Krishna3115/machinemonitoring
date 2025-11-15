import "./ComplaintCountList.css";
import React, { useEffect, useState } from 'react';
import AdminLayout from '../components/AdminLayout';
import axios from 'axios';
import API_BASE_URL from "../apiConfig";
import { useNavigate } from 'react-router-dom';

export default function ComplaintList() {
  const [complaints, setComplaints] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedComplaint, setSelectedComplaint] = useState(null);
  const [resolutionText, setResolutionText] = useState("");
   const navigate = useNavigate();

  useEffect(() => {
    fetchComplaints();
  }, []);

  const fetchComplaints = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/api/complaints/pending`);
      setComplaints(response.data);
    } catch (error) {
      console.error("Error fetching complaints:", error);
    }
  };

  const handleSubmitResolution = async () => {
    if (!resolutionText.trim()) {
      alert("Please enter resolution details.");
      return;
    }

    try {
      await axios.put(`${API_BASE_URL}/api/complaints/${selectedComplaint.id}/resolve`, {
        resolution: resolutionText,
      });

      setComplaints(complaints.filter(c => c.id !== selectedComplaint.id));
      setShowModal(false);
      setResolutionText("");
    } catch (error) {
      console.error("Error resolving complaint:", error);
      alert("Failed to resolve complaint.");
    }
  };

  const handleDownload = async (url, id) => {
    try {
      const response = await fetch(`${API_BASE_URL}${url}`, {
        mode: 'cors'
      });
      const blob = await response.blob();
      const blobUrl = window.URL.createObjectURL(blob);

      const link = document.createElement('a');
      link.href = blobUrl;
      link.download = `complaint-${id}.jpg`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      window.URL.revokeObjectURL(blobUrl);
    } catch (err) {
      console.error("Download failed:", err);
      alert("Failed to download the image.");
    }
  };

  return (
    <AdminLayout>
      <div className="complaint-list-container">
        <h2>Active Customer Complaint</h2>
        <table className="complaint-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Model No</th>
              <th>Status</th>
              <th>Division</th>
              <th>Section</th>
              <th>From KM</th>
              <th>To KM</th>
              <th>Issue Remark</th>
              <th>Photo</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {complaints.map((complaint) => (
              <tr key={complaint.id}>
                <td>{complaint.id}</td>
                <td>{complaint.modelNo}</td>
                <td className="status-pending">{complaint.status}</td>
                <td>{complaint.division}</td>
                <td>{complaint.section}</td>
                <td>{complaint.fromKm}</td>
                <td>{complaint.toKm}</td>
                <td className="issue-text">{complaint.machineIssue}</td>
                <td className="photo-column">
                  {complaint.photoUrl ? (
                    <button
                      className="download-btn"
                      onClick={() => handleDownload(complaint.photoUrl, complaint.id)}
                      title="Download Image"
                    >
                      📥
                    </button>
                  ) : (
                    <span>No Image</span>
                  )}
                </td>
                <td>
                  <div className="action-buttons-horizontal">
                    
                  <button
                    className="action-button"
                    onClick={() =>
                      navigate('/admin/assign-tech', {
                        state: { modelNo: complaint.modelNo }
                      })
                    }
                  >
                    Assign Technician
                  </button>
              
                    {/* <button
                      className="action-button resolve-button"
                      onClick={() => {
                        setSelectedComplaint(complaint);
                        setShowModal(true);
                      }}
                    >
                      Resolve Issue
                    </button> */}
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {showModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h3>Resolve Complaint (ID: {selectedComplaint?.id})</h3>
            <textarea
              value={resolutionText}
              onChange={(e) => setResolutionText(e.target.value)}
              placeholder="Describe how the issue was resolved..."
            ></textarea>
            <div className="modal-buttons">
              <button onClick={handleSubmitResolution} className="submit-button">Submit</button>
              <button onClick={() => setShowModal(false)} className="cancel-button">Cancel</button>
            </div>
          </div>
        </div>
      )}
    </AdminLayout>
  );
}
