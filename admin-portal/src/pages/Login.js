import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import railwayGif from "../assets/railway_back.gif";
import "./Login.css"; // Make sure this file exists
import adminVideo from '../assets/illuman.mp4';
import companyLogo from '../assets/Chakradhar_logo.png';
import API_BASE_URL from "../apiConfig";


export default function Login() {
  const [mobileNumber, setMobileNumber] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

      const handleLogin = async (e) => {
  e.preventDefault();
  try {
    const res = await axios.post(`${API_BASE_URL}/api/users/login`, {
      mobileNumber,
      password,
    }, {
      headers: {
        "ngrok-skip-browser-warning": "true" // ✅ Important for mobile/ngrok
      }
    });

    const { token, name, role, user_id } = res.data; // Removed profileComplete usage
    localStorage.setItem("token", token);
    localStorage.setItem("name", name);
    localStorage.setItem("role", role);
    localStorage.setItem("user_id", user_id || "");
    localStorage.setItem("userId", user_id || "");
    // localStorage.setItem("profileComplete", profileComplete); // Removed

    if (role === "SUPER_ADMIN") {
      navigate("/superadmin");
    } else if (role === "ADMIN") {
      navigate("/admin");
    } else if (role === "USER") {
      // Always navigate to technician dashboard
      navigate("/technician");
    } else {
      alert("Unauthorized role");
    }
  } catch (err) {
    alert("Login failed: " + (err.response?.data?.message || err.message));
  }
};




   const hour = new Date().getHours();

    let greeting = "";
    if (hour < 12) {
    greeting = "☀️ Good Morning";
    } else if (hour < 18) {
    greeting = "🌤️ Good Afternoon";
    } else {
    greeting = "🌙 Good Evening";
    }


  // Inline style using the imported GIF
  const loginBackground = {
    backgroundImage: `url(${railwayGif})`,
    height: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    backgroundSize: "cover",
    backgroundPosition: "center",
    backgroundRepeat: "no-repeat",
    position: "relative",
    fontFamily: "Arial, sans-serif",
  };

  <div className="greeting-message">{greeting}</div>
 

  return (
    <div style={loginBackground}>
    <div className="greeting-message">{greeting}</div>
    <img src={companyLogo} alt="Company Logo" className="top-logo" />
      <form className="login-form" onSubmit={handleLogin}>

        <video
        src={adminVideo}
        autoPlay
        loop
        muted
        playsInline
        className="login-video"
        />

        <h2>Login Here..! 👇</h2>
        <input
          type="text"
          placeholder="Mobile Number"
          value={mobileNumber}
          onChange={(e) => setMobileNumber(e.target.value)}
          required
          
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}
