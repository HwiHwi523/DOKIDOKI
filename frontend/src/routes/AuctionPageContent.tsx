import Container from '@mui/material/Container';
import SideBar from '../components/auction/sidebar/SideBar';
import SearchBar from '../components/auction/SearchBar';
import ContentsList from '../components/auction/contents/ContentsList';
import Grid from '@mui/material/Grid'; // Grid version 1
import { Outlet, Link, useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Tooltip from '@mui/material/Tooltip';


const AuctionPageContent = () => {
  const [size, setSize] = useState(12);
  //query키로 사용할 키 값
  const [category, setCategory] = useState(0);
  const [keyword, setKeyword] = useState('');
  
  const navigate = useNavigate();

  useEffect(() => {
    console.log('auctionPageContent : category : ', category, ' keyword : ', keyword);
    
  }, []);

  return (
    <>
      <Container >
        <Tooltip title="글 작성하기" placement="top">
          <StyledIcon
            onClick={() => {
              navigate('/regist');
            }}
          >
            +
          </StyledIcon>
        </Tooltip>

        <SearchBar setKeyword={setKeyword} setSize={setSize} size={size} />
        <Grid container spacing={2}>
          <Grid item xs={2}>
            <SideBar setCategory={setCategory} />
          </Grid>
          <Grid xs={9}>
            <ContentsList size={size} category={category} keyword={keyword} />
          </Grid>
          <Grid xs={1}></Grid>
        </Grid>
      </Container>
    </>
  );
};

export default AuctionPageContent;

const StyledIcon = styled.div`
  z-index: 10;
  position: fixed;
  right: 10%;
  top: 90%;
  background-color: #3a77ee;
  border-radius: 100px;
  font-size: 50px;
  color: white;
  font-weight: bold;
  width: 60px;
  height: 60px;
  text-align: center;
  line-height: 50px;
  cursor: pointer;
  transition: all 0.3s;
  &:hover {
    top: 89.5%;
    box-shadow: 1px 1rem 15px #dddddd;
  }
  &:active {
    background-color: #4285f4;
  }
`;
