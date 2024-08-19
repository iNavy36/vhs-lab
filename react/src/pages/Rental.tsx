import { useEffect, useState, Dispatch } from "react";

import "./Rental.css";
import { IVhs, IVhsList } from "../pages/Vhs";

import Title from "../components/Title";
import Table from "../components/Table";

export interface IRental {
  rental_id: number;
  rental_date: Date;
  return_date?: Date;
  movie_title: string;
  user_name: string;
}

export interface IRentals extends Array<IRental> {}

function Rental() {
  const [rentals, setRentals] = useState<IRentals>([]);
  const apiRentalUrl: URL = new URL("http://localhost:8080/api/rental");
  useEffect(() => {
    getData(apiRentalUrl);
  }, []);
  const getData = async (url: URL) => {
    fetch(url, { credentials: "same-origin" })
      .then((response) => {
        if (!response.ok) {
          console.error(`Did not get an ok. got: ` + response.statusText);
        }
        return response.json() as Promise<IRentals>;
      })
      .then((json) => {
        if (json.length != 0) setRentals(json);
      });
  };
  return (
    <>
      <Title />
      <div className="tableDiv">
        <div className="rentallist">
          <Table rentals={rentals} />
        </div>
      </div>
      <div className="card">
        <a href="/vhs">VHS Rent & Return</a>
      </div>
    </>
  );
}

export default Rental;
