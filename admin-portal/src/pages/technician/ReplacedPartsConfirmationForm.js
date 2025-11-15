import React, { useEffect, useState } from 'react';
import axios from 'axios';
import API_BASE_URL from '../../apiConfig';
import './ReplacedPartsConfirmationForm.css';

const ReplacedPartsConfirmationForm = () => {
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [partsStatus, setPartsStatus] = useState([]);
  const [partsReceived, setPartsReceived] = useState(false);

  const technicianId = localStorage.getItem("userId");

  // Fetch assigned requests on mount or when technicianId changes
  const fetchAssignments = async () => {
    if (!technicianId) {
      console.error("Technician ID is missing");
      setLoading(false);
      return;
    }

    try {
      const res = await axios.get(`${API_BASE_URL}/api/parts-replacement/assigned-to-replacing-technician`, {
        params: { technicianId },
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });

      setRequests(res.data || []);
    } catch (err) {
      console.error("Error fetching assigned requests:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAssignments();
  }, [technicianId]);

  // When a request is selected, initialize partsStatus state with data from DB
  const handleSelectRequest = (req) => {
    setSelectedRequest(req);
    setPartsReceived(false);

    const initParts = req.parts.map((p) => ({
      id: p.id,
      old_part_name: p.partName,
      old_part_no: p.oldPartNo,
      replaced_part_no: p.replacedPartNo || '',
      machine_serial_no: p.machineSerialNo || '',
    }));

    setPartsStatus(initParts);
  };

  // Mark parts as received — shows form to confirm parts replacement details
  const confirmPartsReceived = () => {
    setPartsReceived(true);
  };

  // Handle change in replaced_part_no or machine_serial_no fields for each part
  const handlePartChange = (index, field, value) => {
    setPartsStatus((prev) => {
      const updated = [...prev];
      updated[index] = {
        ...updated[index],
        [field]: value,
      };
      return updated;
    });
  };

  // Handle submission of replaced parts confirmation form
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate required fields
    for (let p of partsStatus) {
      if (!p.replaced_part_no.trim() || !p.machine_serial_no.trim()) {
        alert("Please fill in all required fields.");
        return;
      }
    }

    const payload = {
      requestId: selectedRequest.id,
      replacedParts: partsStatus.map((p) => ({
        id: p.id,
        replacedPartNo: p.replaced_part_no.trim(),
        machineSerialNo: p.machine_serial_no.trim(),
      })),
    };

    try {
      await axios.post(`${API_BASE_URL}/api/parts-replacement/confirm`, payload, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      });

      alert("Replaced parts confirmed successfully!");

      // Reset states to show request list again
      setSelectedRequest(null);
      setPartsStatus([]);
      setPartsReceived(false);
      setLoading(true);
      await fetchAssignments();
    } catch (err) {
      console.error("Error confirming replacement:", err);
      alert("Failed to confirm replaced parts.");
    }
  };

  if (loading) return <p>Loading...</p>;

  return (
    <div className="confirmation-form-container">
      <h2>🔧 Technician Panel - Confirm Parts</h2>

      {/* Show list if no request selected */}
      {!selectedRequest ? (
        <>
          {requests.length === 0 ? (
            <p>No assigned replacement requests.</p>
          ) : (
            <ul className="request-list">
              {requests.map((req) => (
                <li key={req.id} className="request-item">
                  <strong>Machine:</strong> {req.machine_no} <br />
                  <strong>Date/Time:</strong> {req.datetime}
                  <br />
                  <button
                    className="confirm-btn"
                    onClick={() => handleSelectRequest(req)}
                  >
                    Confirm Parts
                  </button>
                </li>
              ))}
            </ul>
          )}
        </>
      ) : !partsReceived ? (
        // Show parts received confirmation
        <div className="parts-received-confirmation">
          <h3>Have you received the parts for machine {selectedRequest.machine_no}?</h3>
          <button onClick={confirmPartsReceived} className="confirm-btn">
            Yes, parts received
          </button>
          <button onClick={() => setSelectedRequest(null)} className="cancel-btn">
            Cancel
          </button>
        </div>
      ) : (
        // Show parts replacement form prefilled with DB data
        <form onSubmit={handleSubmit}>
          {partsStatus.map((p, idx) => (
            <div className="part-row" key={idx}>
              <div className="form-group">
                <label>Old Part Name</label>
                <input type="text" value={p.old_part_name} disabled />
              </div>

              <div className="form-group">
                <label>Old Part No.</label>
                <input type="text" value={p.old_part_no} disabled />
              </div>

              <div className="form-group">
                <label className="required">Replaced Part No.</label>
                <input
                  type="text"
                  value={p.replaced_part_no}
                  onChange={(e) => handlePartChange(idx, 'replaced_part_no', e.target.value)}
                  required
                />
              </div>

              <div className="form-group">
                <label className="required">Machine Serial No.</label>
                <input
                  type="text"
                  value={p.machine_serial_no}
                  onChange={(e) => handlePartChange(idx, 'machine_serial_no', e.target.value)}
                  required
                />
              </div>
            </div>
          ))}

          <button type="submit" className="submit-btn">
            ✅ Confirm Replacement
          </button>
          <button
            type="button"
            className="cancel-btn"
            onClick={() => setSelectedRequest(null)}
          >
            Cancel
          </button>
        </form>
      )}
    </div>
  );
};

export default ReplacedPartsConfirmationForm;
