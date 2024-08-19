import { useEffect, useState } from "react";

import "./Vhs.css";

import Title from "../components/Title";
import Table from "../components/Table";
import Calendar from "../components/Calendar";
import Popup from "../components/Popup";
import { IRentals } from "./Rental";

interface Props {
  user_id: number | undefined;
  user_name: string | undefined;
}

export interface IVhs {
  id: number;
  title: string;
  rent: number;
}

export interface IVhsList extends Array<IVhs> {}

function Vhs(props: Props) {
  const [vhs, setVhs] = useState<IVhsList>([]);
  const apiVhsUrl: URL = new URL("http://localhost:8080/api/vhs");
  const [unreturned, setUnreturned] = useState<IRentals>([]);
  const unreturnedUrl: URL = new URL(
    "http://localhost:8080/api/rental/user/" +
      (props.user_id != null ? props.user_id : 2)
  );
  useEffect(() => {
    getData(apiVhsUrl);
    getUserUnreturned(unreturnedUrl);
  }, []);
  const getData = async (url: URL) => {
    fetch(url, { credentials: "same-origin" })
      .then((response) => {
        if (!response.ok) {
          console.error(`Did not get an ok. got: ` + response.statusText);
        }
        return response.json() as Promise<IVhsList>;
      })
      .then((json) => {
        if (json.length != 0) setVhs(json);
      });
  };
  const getUserUnreturned = async (url: URL) => {
    fetch(url, { credentials: "same-origin" })
      .then((response) => {
        if (!response.ok) {
          console.error(`Did not get an ok. got: ` + response.statusText);
        }
        return response.json() as Promise<IRentals>;
      })
      .then((json) => {
        if (json.length != 0) setUnreturned(json);
      });
  };

  const [vhsId, setVhsId] = useState<number>(0);
  const newRentalVhsId = (id: number) => {
    //console.log(id);
    setVhsId(id);
  };

  const [date, setDate] = useState<string>();
  const updateDateFromCalendar = (date: string) => {
    if (date != null) setDate(date);
  };

  const sendNewRental = () => {
    console.log(vhsId + " " + date);
    fetch("http://localhost:8080/api/rental", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        user_id: props.user_id != null ? props.user_id : 2, //hardcoded, change later
        vhs_id: vhsId,
        rent_date: date,
      }),
    }).then((response) => {
      if (!response.ok) {
        console.error(`Did not get an ok. got: ` + response.statusText);
        setMessage("Renting failed, VHS already rented before this date.");
        setTrigger(true);
      } else {
        console.log(`Got ok`);
        setMessage("Renting VHS successful.");
        setTrigger(true);
      }
    });
  };

  const [message, setMessage] = useState<string>();
  const [trigger, setTrigger] = useState<boolean>(false);

  const popupOnClose = () => {
    setTrigger(false);
    window.location.reload();
  };

  return (
    <>
      <Title />
      <div className="left tableDiv vhslist">
        <Table
          vhs={vhs}
          onRowClick={(vhsId: number) => newRentalVhsId(vhsId)}
        />
      </div>
      <div className="right datePicker">
        <div
          style={{ visibility: vhsId > 0 ? "visible" : "hidden" }}
          className="calendar"
        >
          <Calendar
            onDateClick={(date: string) => updateDateFromCalendar(date)}
            onDateSubmit={() => sendNewRental()}
            vhs={vhsId}
          />
        </div>
      </div>
      <div className="card cardTable">
        <h2>
          Unreturned Rentals From User{" "}
          {props.user_id != null ? props.user_name : "..."}
        </h2>
        <div className="userRentals tableDiv">
          <Table userRentals={unreturned} />
        </div>
      </div>
      <div className="card">
        <a href="/rental">All Rentals</a>
      </div>
      <Popup trigger={trigger} popupOnClose={popupOnClose}>
        <h3>{message}</h3>
      </Popup>
    </>
  );
}

export default Vhs;
