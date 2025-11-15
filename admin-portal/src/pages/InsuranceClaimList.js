import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AdminLayout from '../components/AdminLayout';
import API_BASE_URL from '../apiConfig';
import './InsuranceClaimList.css';

export default function InsuranceClaimList() {
  const [claims, setClaims] = useState([]);
  const [filteredClaims, setFilteredClaims] = useState([]);
  const [statusFilter, setStatusFilter] = useState('processing');
  const [dateFilter, setDateFilter] = useState('');

  const auth = {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  };

  useEffect(() => {
    fetchClaims();
  }, []);

  useEffect(() => {
    applyFilters();
  }, [claims, statusFilter, dateFilter]);

  const fetchClaims = async () => {
    try {
      const res = await axios.get(`${API_BASE_URL}/api/insurance-claims/all`, auth);
      setClaims(res.data);
    } catch (err) {
      console.error('Error fetching insurance claims:', err);
    }
  };

  const applyFilters = () => {
    let filtered = [...claims];

    if (statusFilter === 'processing') {
      filtered = filtered.filter(
        (c) => c.status !== 'closed' && c.status !== 'failed' && c.status !== 'passed'
      );
    } else if (statusFilter === 'passed') {
      filtered = filtered.filter((c) => c.claimPassed === true);
      if (dateFilter) {
        filtered = filtered.filter((c) =>
          c.closedAt?.startsWith(dateFilter)
        );
      }
    } else if (statusFilter === 'failed') {
      filtered = filtered.filter((c) => c.claimPassed === false);
    }

    setFilteredClaims(filtered);
  };

  const handlePrint = () => {
    window.print();
  };

  return (
    <AdminLayout>
      <div className="insurance-claims-page">

        {/* Header */}
        <h2>Insurance Claims Management</h2>

        {/* Filter Buttons */}
        <div className="filter-buttons">
          <button
            className={statusFilter === 'processing' ? 'active' : ''}
            onClick={() => {
              setStatusFilter('processing');
              setDateFilter('');
            }}
          >
            🛠 Processing Claims
          </button>
          <button
            className={statusFilter === 'passed' ? 'active' : ''}
            onClick={() => setStatusFilter('passed')}
          >
            ✅ Passed Claims
          </button>
          <button
            className={statusFilter === 'failed' ? 'active' : ''}
            onClick={() => {
              setStatusFilter('failed');
              setDateFilter('');
            }}
          >
            ❌ Failed Claims
          </button>
        </div>

        {/* Date Filter (only for passed) */}
        {statusFilter === 'passed' && (
          <div className="date-filter">
            <label>Filter by Close Date:</label>
            <input
              type="date"
              value={dateFilter}
              onChange={(e) => setDateFilter(e.target.value)}
            />
          </div>
        )}

        {/* Print Button */}
        <div className="print-section">
          <button onClick={handlePrint}>🖨 Print</button>
        </div>

        {/* Printable Area */}
<div className="printable-area">
  {/* ✅ This only shows during print */}
  <div className="print-only">
    <h1>Chakradhar Industries</h1>
    <p><strong>Insurance Claims Report</strong></p>
    <hr />
  </div>

  {/* Claims Table */}
  <table className="claims-table">
    <thead>
  <tr>
    <th>ID</th>
    <th>Ref No.</th>
    <th>Machine Serial</th>
    <th>Division</th> {/* ✅ New */}
    <th>Section</th> {/* ✅ New */}
    <th>Complaint Date</th>
    <th>Status</th>
    <th>Joint Report Date</th>
    <th>Submitted</th>
    <th>Surveyor Visit</th>
    <th>Closed</th>
    <th>Claim Passed</th>
    <th>Remark</th>
  </tr>
</thead>

    <tbody>
      {filteredClaims.map((claim) => (
        <tr key={claim.id}>
          <td>{claim.id}</td>
          <td>{claim.complaintNo || claim.referenceNo || '—'}</td>
          <td>{claim.machineSerial || '—'}</td>

                {/* ✅ NEW */}
      <td>{claim.division || '—'}</td>
      <td>{claim.section || '—'}</td>
      
          <td>{claim.complaintDate || '—'}</td>
          <td>{claim.status}</td>
          <td>{claim.jointReportDate || '—'}</td>
          <td>{claim.claimedToInsuranceAt ? new Date(claim.claimedToInsuranceAt).toLocaleString() : '—'}</td>
          <td>{claim.serverVisitedAt ? new Date(claim.serverVisitedAt).toLocaleString() : '—'}</td>
          <td>{claim.closedAt ? new Date(claim.closedAt).toLocaleString() : '—'}</td>
          <td style={{
            color: claim.claimPassed
              ? 'green'
              : claim.claimPassed === false
              ? 'red'
              : 'black',
          }}>
            {claim.claimPassed == null
              ? 'Pending'
              : claim.claimPassed
              ? 'Yes'
              : 'No'}
          </td>
         <td>
          {claim.remark || '—'}
          {statusFilter === 'passed' && (
            <div>
              <a href={`/admin/claim-summary/${claim.id}`} className="view-summary-btn">
                📄 View Summary
              </a>
            </div>
          )}
        </td>

        </tr>
      ))}
    </tbody>

  </table>
</div>
      </div>
    </AdminLayout>
  );
}
