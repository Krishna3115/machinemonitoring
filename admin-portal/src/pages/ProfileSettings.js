import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AdminLayout from '../components/AdminLayout';
import API_BASE_URL from '../apiConfig';
import './ProfileSettings.css';

export default function ProfileSettings() {
  const [profile, setProfile] = useState(null);
  const [darkMode, setDarkMode] = useState(false);

  useEffect(() => {
    fetchProfile();

    // Load saved theme from localStorage and apply it
    const savedTheme = localStorage.getItem('theme') || 'light';
    setDarkMode(savedTheme === 'dark');
    document.body.className = savedTheme;
  }, []);

  const fetchProfile = async () => {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`${API_BASE_URL}/api/users/profile`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setProfile(res.data);
    } catch (err) {
      console.error('Failed to fetch profile:', err);
    }
  };

  const toggleTheme = () => {
    const newTheme = darkMode ? 'light' : 'dark';
    document.body.className = newTheme;
    localStorage.setItem('theme', newTheme);
    setDarkMode(!darkMode);
  };

  return (
    <AdminLayout>
      <div className="profile-settings-container">
        <h2>Admin Profile</h2>

        {profile ? (
          <div className="profile-card">
            <img
              src={`${API_BASE_URL}${profile.profilePhotoUrl}`}
              alt="Profile"
              className="profile-photo"
            />
            <p><strong>Name:</strong> {profile.name}</p>
            <p><strong>Email:</strong> {profile.email}</p>
            <p><strong>Mobile Number:</strong> {profile.mobileNumber}</p>
            <p><strong>Emergency Contact:</strong> {profile.emergencyContactNumber}</p>
            <p><strong>City:</strong> {profile.city}</p>
            <p><strong>Address:</strong> {profile.address}</p>
            <p><strong>Role:</strong> {profile.role}</p>
            <p>
              <strong>ID Proof:</strong>{' '}
              <a
                href={`${API_BASE_URL}${profile.idProofUrl}`}
                target="_blank"
                rel="noopener noreferrer"
              >
                View ID
              </a>
            </p>
          </div>
        ) : (
          <p>Loading profile...</p>
        )}

        <div className="theme-toggle"> 
          <span>Theme:</span>
          <button onClick={toggleTheme}>
            {darkMode ? '🌙 Dark Mode' : '☀️ Light Mode'}
          </button>
        </div>
      </div>
    </AdminLayout>
  );
}
