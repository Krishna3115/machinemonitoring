import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';
import AdminLayout from '../components/AdminLayout';
import './InsuranceClaimDetails.css';
import API_BASE_URL from '../apiConfig';

export default function InsuranceClaimDetails() {
  const { reportId } = useParams();
  const location = useLocation();
  const navigate = useNavigate();

  const [claim, setClaim] = useState(null);
  const [parts, setParts] = useState([]);
  const [reportDate, setReportDate] = useState('');
  const [reportFile, setReportFile] = useState(null);

  // Get query params to capture machineSerial, complaintNo, complaintDate
  const query = new URLSearchParams(location.search);
  const machineSerial = query.get('machineSerial') || '';
  const complaintNo = query.get('complaintNo') || '';
  const complaintDate = query.get('complaintDate') || '';

  const auth = {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  };

  useEffect(() => {
    // Fetch claim only if reportId is valid
    if (!claim && complaintNo && complaintDate && machineSerial) {
      axios
        .post(
          `${API_BASE_URL}/api/insurance-claims/${reportId}/start`,
          { complaintNo, complaintDate, machineSerial },
          auth
        )
        .then(fetchClaim)
        .catch(err => console.error(err));
    } else {
      fetchClaim();
    }
  }, [reportId, complaintNo, complaintDate, machineSerial]);

  const fetchClaim = async () => {
    try {
      const res = await axios.get(`${API_BASE_URL}/api/insurance-claims/${reportId}`, auth);
      setClaim(res.data);
    } catch (err) {
      console.error('Error fetching claim:', err.response?.data || err.message);
    }
  };

  const addPart = () => {
    setParts([...parts, { partName: '', actualValue: 0, claimedAmount: 0 }]);
  };

  const updatePart = (index, field, value) => {
    const updated = [...parts];
    updated[index][field] = value;
    setParts(updated);
  };

  const removePart = (index) => {
    const updated = parts.filter((_, i) => i !== index);
    setParts(updated);
  };

  const submitParts = async () => {
    try {
      await axios.post(`${API_BASE_URL}/api/insurance-claims/${claim.id}/parts`, parts, auth);
      await axios.post(`${API_BASE_URL}/api/insurance-claims/${reportId}/close`, {}, auth);
      alert('Claim parts submitted and claim closed!');
      fetchClaim();
    } catch (err) {
      console.error('Error submitting claim parts:', err);
      alert('Error saving parts!');
    }
  };

  const handleFileUpload = async () => {
    if (!reportDate || !reportFile) {
      alert('Please select both a date and a file.');
      return;
    }

    const formData = new FormData();
    formData.append('file', reportFile);
    formData.append('date', reportDate);

    try {
      await axios.post(
        `${API_BASE_URL}/api/insurance-claims/${reportId}/upload-joint-report`,
        formData,
        {
          ...auth,
          headers: {
            ...auth.headers,
            'Content-Type': 'multipart/form-data',
          },
        }
      );
      alert('Joint report uploaded successfully!');
      setReportFile(null);
      setReportDate('');
      fetchClaim();
    } catch (err) {
      console.error('Upload failed:', err);
      alert('Failed to upload report.');
    }
  };

  const onSubmitToInsurance = async () => {
    await axios.post(`${API_BASE_URL}/api/insurance-claims/${reportId}/submit`, {}, auth);
    fetchClaim();
  };

  const onMarkServerVisitDone = async () => {
    await axios.post(`${API_BASE_URL}/api/insurance-claims/${reportId}/server-visit`, {}, auth);
    fetchClaim();
  };

  const onUpdateResult = async (passed) => {
    const remark = passed ? '' : prompt('Enter reason for rejection:');
    await axios.post(`${API_BASE_URL}/api/insurance-claims/${reportId}/result`, null, {
      ...auth,
      params: { passed, remark },
    });
    fetchClaim();
  };

  const onCloseClaim = async () => {
    await axios.post(`${API_BASE_URL}/api/insurance-claims/${reportId}/close`, {}, auth);
    fetchClaim();
  };

  if (!claim) {
    return (
      <AdminLayout>
        <p>Loading claim <span className="loader"></span></p>
      </AdminLayout>
    );
  }

  return (
    <AdminLayout>
      <div className="claim-details">
        <h2>Insurance Claim - Report #{claim.vandalismReportId}</h2>

        {/* Show complaint details (this should work now on all page reloads) */}
        <p><strong>Machine Serial No.:</strong> {machineSerial}</p>
        <p><strong>Complaint No.:</strong> {complaintNo}</p>
        <p><strong>Complaint Date:</strong> {complaintDate}</p>

        <p><strong>Status:</strong> {claim.status?.replace(/_/g, ' ') || 'N/A'}</p>

        <p><strong>Joint Report:</strong> {claim.jointReportPdf ? (
          <a href={claim.jointReportPdf} target="_blank" rel="noopener noreferrer">View Report</a>
        ) : 'Not uploaded'}</p>

        <p><strong>Joint Report Date:</strong> {claim.jointReportDate || 'N/A'}</p>

        <p><strong>Result:</strong> {
          claim.status === 'closed' && claim.claimPassed == null
            ? '❌ Failed or Closed Without Result'
            : claim.claimPassed == null
              ? 'Pending'
              : claim.claimPassed
                ? '✅ Passed'
                : '❌ Failed'
        }</p>

        <p><strong>Remark:</strong> {claim.remark || '-'}</p>

        {/* Upload Joint Report */}
        {claim.status === 'started' && (
          <>
            <h4>Upload Joint Report</h4>
            <input type="date" value={reportDate} onChange={(e) => setReportDate(e.target.value)} />
            <input type="file" accept="application/pdf" onChange={(e) => setReportFile(e.target.files[0])} />
            <button onClick={handleFileUpload}>Upload</button>
          </>
        )}

        {/* Actions */}
        {claim.status === 'joint_report_uploaded' && (
          <>
            <p>Joint report uploaded. Proceed to submit claim.</p>
            <button onClick={onSubmitToInsurance}>Forward Claim to Insurance</button>
          </>
        )}

        {claim.status === 'submitted_to_insurance' && (
          <>
            <p>Waiting for Surveyor visit confirmation.</p>
            <button onClick={onMarkServerVisitDone}>Confirm Surveyor Visit</button>
          </>
        )}

        {claim.status === 'awaiting_claim_result' && (
          <>
            <p>Surveyor visit completed. Please provide result.</p>
            <button onClick={() => onUpdateResult(true)}>✅ Approve Claim</button>
            <button onClick={() => onUpdateResult(false)}>❌ Reject Claim</button>
          </>
        )}

        {claim.status === 'failed' && (
          <>
            <p><strong>Claim Failed:</strong> {claim.remark}</p>
            <button onClick={onCloseClaim}>Close Claim</button>
          </>
        )}

        {claim.status === 'passed' && (
          <>
            <h4>Enter Claimed Parts</h4>
            <div className="part-input-header">
              <div>Part Name</div>
              <div>Actual Value (₹)</div>
              <div>Claimed Amount (₹)</div>
              <div>Actions</div>
            </div>
            {parts.map((part, index) => (
              <div key={index} className="part-input-row">
                <input type="text" value={part.partName} onChange={(e) => updatePart(index, 'partName', e.target.value)} />
                <input type="number" value={part.actualValue} onChange={(e) => updatePart(index, 'actualValue', parseFloat(e.target.value))} />
                <input type="number" value={part.claimedAmount} onChange={(e) => updatePart(index, 'claimedAmount', parseFloat(e.target.value))} />
                <button onClick={() => removePart(index)}>❌ Remove</button>
              </div>
            ))}
            <button onClick={addPart}>➕ Add Part</button>
            <button onClick={submitParts} disabled={parts.length === 0}>✅ Submit & Close Claim</button>
          </>
        )}

        {claim.status === 'closed' && (
          <p><strong>Claim Closed</strong></p>
        )}

        {/* Progress Tracker */}
        <div className="progress-tracker">
          <h4>Progress</h4>
          <ol>
            <li className="completed">Claim Started</li>
            <li className={claim.jointReportPdf ? 'completed' : ''}>Joint Report Uploaded</li>
            <li className={['submitted_to_insurance', 'awaiting_claim_result', 'passed', 'failed', 'closed'].includes(claim.status) ? 'completed' : ''}>Submitted to Insurance</li>
            <li className={['awaiting_claim_result', 'passed', 'failed', 'closed'].includes(claim.status) ? 'completed' : ''}>Surveyor Visit</li>
            <li className={['passed', 'failed', 'closed'].includes(claim.status) ? 'completed' : ''}>Claim Result</li>
            <li className={claim.status === 'closed' ? 'completed' : ''}>Closed</li>
          </ol>
        </div>
      </div>
    </AdminLayout>
  );
}
