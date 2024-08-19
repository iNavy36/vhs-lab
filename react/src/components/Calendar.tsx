import { getTodayDate } from "@mui/x-date-pickers/internals";
import "./Calendar.css";

import { useEffect, useState } from "react";

interface Props {
  onDateClick: (date: string) => void;
  onDateSubmit: () => void;
  vhs: number;
}

function Calendar(props: Props) {
  const [date, setDate] = useState<string>("");
  const [disabled, setDisabled] = useState<boolean>(true);
  useEffect(() => {
    if (props.vhs <= 0) setDate("");
  }, [props.vhs]);
  useEffect(() => {
    if (date != "") {
      setDisabled(false);
    } else {
      setDisabled(true);
    }
  }, [date]);

  const todayDate = (): string => {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, "0");
    var mm = String(today.getMonth() + 1).padStart(2, "0"); //January is 0!
    var yyyy = today.getFullYear();

    return yyyy + "-" + mm + "-" + dd;
  };

  return (
    <>
      <form
        className="calendar"
        onSubmit={(e) => {
          e.preventDefault();
          props.onDateSubmit();
        }}
      >
        <label className="calendar">
          Choose date of rent:
          <input
            className="calendar"
            type="date"
            value={date}
            min={todayDate()}
            onKeyDown={(e) => e.preventDefault()}
            placeholder="DD/MM/YY"
            onChange={(e) => {
              props.onDateClick(e.target.value);
              setDate(e.target.value);
            }}
          />
        </label>
        <input
          className="submitButton"
          type="submit"
          value="Submit"
          disabled={disabled}
        ></input>
      </form>
    </>
  );
}

export default Calendar;
