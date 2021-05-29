import './Home.css';
import {authProvider} from "../../App";
import {Button} from "reactstrap";

const Home = () => {

    function onClick() {
        authProvider.logout()
    }

    return (
        <Button onClick={onClick} />
    );
}

export default Home;
