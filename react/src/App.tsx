import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Index from "./pages/Index";
import Rental from "./pages/Rental";
import Vhs from "./pages/Vhs";
import { useEffect, useState } from "react";

interface User {
  user_id: number;
  user_name: string;
}

function App() {
  const [user, setUser] = useState<User>();
  useEffect(() => {}, []);
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Index />} />
        <Route
          path="/vhs"
          element={<Vhs user_id={user?.user_id} user_name={user?.user_name} />}
        />
        <Route path="/rental" element={<Rental />} />
      </Routes>
    </Router>
  );
}

export default App;
