import React from 'react';
import "./Loader.css";  // create this CSS file to style the loader
import loadingGif from '../assets/train.gif';

export default function Loader() {
  return (
    <div className="loader-container">
      <img src={loadingGif} alt="Loading..." />
    </div>
  );
}