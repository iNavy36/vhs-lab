import React from "react";
import "./Popup.css";

interface Props {
  trigger: boolean;
  children: React.ReactNode;
  popupOnClose: () => void;
}

function Popup(props: Props) {
  return props.trigger ? (
    <div className="popup">
      <div className="popup-inner">
        <button className="close-btn" onClick={props.popupOnClose}>
          x
        </button>
        {props.children}
      </div>
    </div>
  ) : (
    ""
  );
}

export default Popup;
