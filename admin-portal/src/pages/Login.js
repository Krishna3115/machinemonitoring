import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import railwayGif from "../assets/railway_back.gif";
import "./Login.css";
import adminVideo from "../assets/illuman.mp4";
import companyLogo from "../assets/chakradhar new1.png";
import API_BASE_URL from "../apiConfig";

export default function Login() {
  const [mobileNumber, setMobileNumber] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  // =========================
  // 🔹 PWA Install Logic
  // =========================
  const [deferredPrompt, setDeferredPrompt] = useState(null);
  const [showInstallButton, setShowInstallButton] = useState(false);

  useEffect(() => {
    const handler = (e) => {
      e.preventDefault();
      setDeferredPrompt(e);
      setShowInstallButton(true);
    };

    window.addEventListener("beforeinstallprompt", handler);

    return () => {
      window.removeEventListener("beforeinstallprompt", handler);
    };
  }, []);

  // =========================
  // 🔹 Login Logic
  // =========================
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post(
        `${API_BASE_URL}/api/users/login`,
        {
          mobileNumber,
          password,
        },
        {
          headers: {
            "ngrok-skip-browser-warning": "true",
          },
        }
      );

      const { token, name, role, user_id } = res.data;

      localStorage.setItem("token", token);
      localStorage.setItem("name", name);
      localStorage.setItem("role", role);
      localStorage.setItem("user_id", user_id || "");
      localStorage.setItem("userId", user_id || "");

      if (role === "SUPER_ADMIN") {
        navigate("/superadmin");
      } else if (role === "ADMIN") {
        navigate("/admin");
      } else if (role === "USER") {
        navigate("/technician");
      } else {
        alert("Unauthorized role");
      }
    } catch (err) {
      alert("Login failed: " + (err.response?.data?.message || err.message));
    }
  };

  // =========================
  // 🔹 Greeting Message
  // =========================
  const hour = new Date().getHours();
  let greeting = "";
  if (hour < 12) {
    greeting = "☀️ Good Morning";
  } else if (hour < 18) {
    greeting = "🌤️ Good Afternoon";
  } else {
    greeting = "🌙 Good Evening";
  }

  // =========================
  // 🔹 Background Style
  // =========================
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

        {/* 🔹 LOGIN BUTTON */}
        <button type="submit">Login</button>

        {/* 🔹 DOWNLOAD ANDROID APP BUTTON */}
        {showInstallButton && (
          <button
            type="button"
            className="install-button"
            onClick={async () => {
              if (!deferredPrompt) return;
              deferredPrompt.prompt();
              await deferredPrompt.userChoice;
              setDeferredPrompt(null);
              setShowInstallButton(false);
            }}
          >
            📱 Download Android App
          </button>
        )}
      </form>
    </div>
  );
}
