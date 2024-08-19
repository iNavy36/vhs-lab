import "./Table.css";

import { IRentals } from "../pages/Rental";
import { IVhs, IVhsList } from "../pages/Vhs";
import { useState } from "react";
import Popup from "./Popup";

interface Props {
  rentals?: IRentals | undefined;
  vhs?: IVhsList | undefined;
  onRowClick?: (id: number) => void;
  userRentals?: IRentals | undefined;
}

function Table(props: Props) {
  if (props.rentals != null) {
    if (props.rentals!.length == 0) {
      return <p>There are still no rentals.</p>;
    } else {
      return (
        <table>
          <thead>
            <tr>
              <th>Rental Id</th>
              <th>Rent Date</th>
              <th>Return Date</th>
              <th>User Name</th>
              <th>VHS Tape - Movie Name</th>
            </tr>
          </thead>
          <tbody>
            {props.rentals!.map((rental) => {
              return (
                <tr key={rental.rental_id}>
                  <td>{rental.rental_id}</td>
                  <td>{rental.rental_date.toString()}</td>
                  <td>
                    {rental.return_date == null
                      ? "-"
                      : rental.return_date.toString()}
                  </td>
                  <td>{rental.user_name}</td>
                  <td>{rental.movie_title}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      );
    }
  }
  if (props.vhs != null) {
    if (props.vhs!.length == 0) {
      return <p>There are no VHS tapes available.</p>;
    } else {
      const formatData = (items: IVhsList) => {
        return items.map((item: IVhs) => ({ ...item, isActiveBefore: false }));
      };

      const [vhsSelected, setVhsSelected] = useState(formatData(props.vhs));
      const handleColorChange = (id: number) => {
        setVhsSelected((prevState) => {
          return prevState.map((item) => {
            if (item.isActiveBefore) {
              return { ...item, isActiveBefore: !item.isActiveBefore };
            } else if (item.id === id) {
              return { ...item, isActiveBefore: !item.isActiveBefore };
            } else {
              return item;
            }
          });
        });
      };

      const rowClick = async (id: number) => {
        if (vhsSelected[id - 1].isActiveBefore) props.onRowClick!(0);
        else props.onRowClick!(id);
      };

      return (
        <table>
          <thead>
            <tr>
              <th>VHS Tape - Movie Name</th>
              <th>Late Rent Fee</th>
            </tr>
          </thead>
          <tbody className="vhslist">
            {vhsSelected.map((v) => {
              return (
                <tr
                  key={v.id}
                  onClick={() => {
                    rowClick(v.id);
                    handleColorChange(v.id);
                  }}
                  className={v.isActiveBefore ? "selected" : ""}
                >
                  <td>{v.title}</td>
                  <td>{(Math.round(v.rent * 100) / 100).toFixed(2)}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      );
    }
  }
  if (props.userRentals != null) {
    if (props.userRentals!.length == 0) {
      return <p>User does not have unreturned tapes.</p>;
    } else {
      const [message, setMessage] = useState<string>();
      const [trigger, setTrigger] = useState<boolean>(false);

      const popupOnClose = () => {
        setTrigger(false);
        window.location.reload();
      };

      const returnTape = (id: number) => {
        fetch("http://localhost:8080/api/rental/" + id, {
          method: "PUT",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
        })
          .then((response) => {
            if (!response.ok) {
              console.error(`Did not get an ok. got: ` + response.statusText);
              setMessage("Returning of VHS failed.");
              setTrigger(true);
            } else {
              console.log(`Got ok`);
              return response.json() as Promise<number>;
            }
          })
          .then((value) => {
            if (value != null) {
              setMessage(
                "Returning VHS successful, due rent: " +
                  (Math.round(value * 100) / 100).toFixed(2)
              );
            } else {
              setMessage("An error occured.");
            }
            setTrigger(true);
          });
      };

      return (
        <>
          <table className="userRentals">
            <thead>
              <tr>
                <th>Rent Date</th>
                <th>VHS Tape - Movie Name</th>
                <th> </th>
              </tr>
            </thead>
            <tbody>
              {props.userRentals!.map((rental) => {
                return (
                  <tr key={rental.rental_id}>
                    <td>{rental.rental_date.toString()}</td>
                    <td>{rental.movie_title}</td>
                    <td>
                      <button
                        className="returnButton"
                        onClick={() => {
                          returnTape(rental.rental_id);
                        }}
                      >
                        Return
                      </button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
          <Popup trigger={trigger} popupOnClose={popupOnClose}>
            <h3>{message}</h3>
          </Popup>
        </>
      );
    }
  }
}
export default Table;
