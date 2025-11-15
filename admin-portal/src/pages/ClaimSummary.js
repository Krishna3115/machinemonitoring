import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import AdminLayout from '../components/AdminLayout';
import API_BASE_URL from '../apiConfig';
import './ClaimSummary.css';

export default function ClaimSummary() {
  const { claimId } = useParams();
  const [parts, setParts] = useState([]);
  const [summary, setSummary] = useState(null);
  const [loading, setLoading] = useState(true);

  const auth = {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    },
  };

  useEffect(() => {
    async function fetchData() {
      try {
        const [partsRes, summaryRes] = await Promise.all([
          axios.get(`${API_BASE_URL}/api/insurance-claims/${claimId}/parts`, auth),
          axios.get(`${API_BASE_URL}/api/insurance-claims/${claimId}/summary`, auth),
        ]);

        setParts(partsRes.data.map(part => {
          const profitOrLossAmount = part.claimedAmount - part.actualValue;
          const profitOrLossPercent = part.actualValue !== 0
            ? (profitOrLossAmount / part.actualValue) * 100
            : 0;
          return {
            ...part,
            profitOrLossAmount,
            profitOrLossPercent
          };
        }));

        setSummary(summaryRes.data);
      } catch (err) {
        console.error('Error loading claim summary:', err);
      } finally {
        setLoading(false);
      }
    }

    fetchData();
  }, [claimId]);

  if (loading) return <AdminLayout><p>Loading claim summary...</p></AdminLayout>;

  return (
    <AdminLayout>
      <div className="claim-summary">
        <h2>Claim Summary - Claim #{claimId}</h2>

        <h4>Parts Claimed</h4>
        <table className="claim-table">
          <thead>
            <tr>
              <th>Part Name</th>
              <th>Actual Value (₹)</th>
              <th>Claimed Amount (₹)</th>
              <th>Profit/Loss (₹)</th>
              <th>Profit/Loss (%)</th>
            </tr>
          </thead>
          <tbody>
            {parts.map((part, idx) => (
              <tr key={idx}>
                <td>{part.partName}</td>
                <td>₹{part.actualValue.toLocaleString('en-IN')}</td>
                <td>₹{part.claimedAmount.toLocaleString('en-IN')}</td>
                <td style={{ color: part.profitOrLossAmount >= 0 ? 'green' : 'red' }}>
                  ₹{Math.abs(part.profitOrLossAmount).toLocaleString('en-IN')}
                </td>
                <td style={{ color: part.profitOrLossAmount >= 0 ? 'green' : 'red' }}>
                  {part.profitOrLossPercent.toFixed(2)}%
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <h4 style={{ marginTop: '30px' }}>Total Summary</h4>
        {summary ? (
          <ul className="summary-list">
            <li><strong>Total Actual Value:</strong> ₹{summary.totalActualValue.toLocaleString('en-IN')}</li>
            <li><strong>Total Claimed Amount:</strong> ₹{summary.totalClaimedAmount.toLocaleString('en-IN')}</li>
            <li>
              <strong style={{ color: summary.profitOrLossAmount >= 0 ? 'green' : 'red' }}>
                {summary.profitOrLossAmount >= 0 ? 'Total Profit' : 'Total Loss'}:
              </strong>{' '}
              ₹{Math.abs(summary.profitOrLossAmount).toLocaleString('en-IN')} ({summary.profitOrLossPercent.toFixed(2)}%)
            </li>
          </ul>
        ) : (
          <p>Summary data not available.</p>
        )}
      </div>
    </AdminLayout>
  );
}
