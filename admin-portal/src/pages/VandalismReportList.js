import React, { useEffect, useState } from 'react';
import AdminLayout from '../components/AdminLayout';
import axios from 'axios';
import './VandalismReportList.css';
import API_BASE_URL from '../apiConfig';
import { useNavigate } from 'react-router-dom';

export default function VandalismReportList() {
  const [reports, setReports] = useState([]);
  const [selectedImages, setSelectedImages] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate();

  const authHeader = () => ({
    headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
  });

  useEffect(() => {
    fetchReports();
  }, []);

  const fetchReports = async () => {
    try {
      const res = await axios.get(`${API_BASE_URL}/api/vandalism/all-with-names`, authHeader());
      setReports(res.data);
    } catch (err) {
      console.error('Error fetching reports:', err);
    }
  };

  const handleDownload = async (url, filename) => {
    try {
      const res = await fetch(url, { mode: 'cors' });
      const blob = await res.blob();
      const blobURL = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = blobURL;
      link.download = filename || 'report.jpg';
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(blobURL);
    } catch (err) {
      console.error('Download failed:', err);
    }
  };

  const applyForClaim = async (report) => {
  const complaintNo = prompt("Enter Customer Complaint No.");
  if (!complaintNo) return alert("Complaint No is required.");

  const complaintDate = prompt("Enter Complaint Date (YYYY-MM-DD)");
  if (!complaintDate) return alert("Complaint Date is required.");


  localStorage.setItem(`claimMeta-${report.id}`, JSON.stringify({
    complaintNo,
    complaintDate,
    machineSerial: report.modelNo,
  }));

  // Navigate, including details
  navigate(
    `/admin/insurance-claim/${report.id}?complaintNo=${encodeURIComponent(complaintNo)}&complaintDate=${encodeURIComponent(complaintDate)}&machineSerial=${encodeURIComponent(report.modelNo)}`
  );
};


  const renderActionButton = (report) => {
  const status = (report.claimStatus ?? 'NOT_STARTED').toUpperCase();

  if (status === 'NOT_STARTED') {
    return (
      <button className="action-btn" onClick={() => applyForClaim(report)}>
        Apply for Claim
      </button>
    );
  }

  // For existing claims, load meta from localStorage
  const storedMeta = localStorage.getItem(`claimMeta-${report.id}`);
  const meta = storedMeta ? JSON.parse(storedMeta) : {};

  const queryString = new URLSearchParams({
    complaintNo: meta.complaintNo || '',
    complaintDate: meta.complaintDate || '',
    machineSerial: meta.machineSerial || report.modelNo, // Fallback if not stored
  }).toString();

  return (
    <button
      className="action-btn"
      onClick={() => navigate(`/admin/insurance-claim/${report.id}?${queryString}`)}
    >
      Go to Claim
    </button>
  );
};


  return (
    <AdminLayout>
      <div className="technician-list-container">
        <h2>Vandalism Reports</h2>
        <table className="technician-table">
          <thead>
            <tr>
              <th>Inspection ID</th>
              <th>Machine Seriel No</th>
              <th>Component</th>
              <th>Description</th>
              <th>Download</th>
              <th>Reported By</th>
              <th>Reported Date</th>
              <th>Claim Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {reports.map((report) => (
              <tr key={report.id}>
                <td>{report.inspectionId}</td>
                <td>{report.modelNo}</td>
                <td>{report.componentName}</td>
                <td>{report.issueDescription && report.issueDescription.trim() !== '' ? report.issueDescription : 'Machine is working'}</td>

                <td>
                  {report.photoUrls && report.photoUrls.length > 0 ? (
                    <button
                      className="view-btn"
                      onClick={() => {
                        setSelectedImages(report.photoUrls);
                        setShowModal(true);
                      }}
                    >
                      View Images
                    </button>
                  ) : (
                    'No File'
                  )}
                </td>
                <td>{report.reportedByName || 'Unknown'}</td>
                <td>{new Date(report.reportedAtDateTime).toLocaleDateString()}</td>
                <td>{String(report.claimStatus)}</td>
                <td>{renderActionButton(report)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* ✅ Modal for Viewing Images */}
      {showModal && (
        <div className="image-modal-overlay" onClick={() => setShowModal(false)}>
          <div className="image-modal-content" onClick={(e) => e.stopPropagation()}>
            <h3>Report Images</h3>
            <div className="modal-image-list">
              {selectedImages.map((url, idx) => (
                <div key={idx} className="modal-image-item">
                  <img src={url} alt={`Modal ${idx}`} />
                  <button
                    onClick={() => handleDownload(url, `report-image-${idx + 1}.jpg`)}
                    className="modal-download-btn"
                  >
                    ⬇️ Download
                  </button>
                </div>
              ))}
            </div>
            <button className="close-modal-btn" onClick={() => setShowModal(false)}>
              Close ✖️
            </button>
          </div>
        </div>
      )}
    </AdminLayout>
  );
}
