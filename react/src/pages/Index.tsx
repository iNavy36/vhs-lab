import { useState } from "react";
import "./Index.css";
import Title from "../components/Title";

function Index() {
  return (
    <>
      <Title />
      <div className="card">
        <a href="/vhs">VHS Rent & Return</a>
      </div>
      <div className="card">
        <a href="/rental">All Rentals</a>
      </div>
    </>
  );
}

export default Index;
