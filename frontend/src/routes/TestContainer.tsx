import styled, { keyframes } from 'styled-components';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Tooltip from '@mui/material/Tooltip';
import Chip from '@mui/material/Chip';
import { useRecentPopularInfo } from 'src/hooks/recentPopularInfo';
import { KafkaLog } from 'src/datatype/datatype';
import bidImgSrc from '../assets/image/bid.jpg';
import clickImgSrc from '../assets/image/click.jpg';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import Box from '@mui/material/Box';

function numberFormat(price: number | null) {
  return price?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 원';
}

const TestContainer = () => {
  const navigate = useNavigate();

  const { isLoading, isError, data } = useRecentPopularInfo();
  if (isLoading) return <h1>isLoading...</h1>;
  if (isError) return <h1>error occured!</h1>;

  // 최근 30초간 가장 많이 클릭된 auction, 가장 많은 입찰 요청(실패 포함)이 이루어진 auction 배열. 최소 0개~5개
  const clickArr: KafkaLog[] = data.click;
  const bidArr: KafkaLog[] = data.bid;

  console.log('clickArr >> ', clickArr); //가장 많이 방문된 게시글
  console.log('bidArr >> ', bidArr); //입찰이 가장 많일어난 계시글

  return (
    <>
      <Grid container sx={{ width: '100%', padding: '10%' }} gap={5}>
        <Grid item xs={12} alignSelf="flex-start">
          <StyledDiv style={{ padding: '10%', boxSizing: 'border-box', float: 'left', width:"1100px" }}>
            <BidImg src={bidImgSrc} />
            <StyledBidRank style={{ paddingLeft: '300px' }}>
              <Typography variant="subtitle1" color="primary">지금 제일 핫한 경매!</Typography>
              {bidArr.map((data: KafkaLog, i) => {
                return (
                  <AnimationDiv style={{marginTop:"20px"}}>
                    <Tooltip title={data.product_name} arrow placement="top-end">
                      <Box
                        key={i}
                        sx={{ marginTop: '10px', cursor: 'pointer' }}
                        onClick={() => {
                          navigate('/auction/product/' + data.auction_id);
                        }}
                      >
                        <NumberSpan>{i + 1}. </NumberSpan>
                        <Chip size="small" label={data.category_name} variant="outlined" />

{/* <Grid container>
  <Grid item sx={...commentStyle}>

        <Grid item xs={12} alignItems="end" justifyContent={'end'} alignContent="end">
          <StyledDiv style={{ padding: '10%', boxSizing: 'border-box', float: 'right', width:"1100px" }}>
            <ClickImg src={clickImgSrc} />
            <StyledClickRank style={{ paddingRight: '300px'}}>
              <Typography variant="subtitle1" color="primary" textAlign={"end"}>가장 인기 글</Typography>
              {clickArr.map((data: KafkaLog, i) => {
                return (
                  <AnimationDiv style={{textAlign:"end", marginTop:"20px"}}>
                    <Tooltip title={data.product_name} arrow placement="top-start">
                      <Box
                        key={i}
                        sx={{ marginTop: '10px', cursor: 'pointer' }}
                        onClick={() => {
                          navigate('/auction/product/' + data.auction_id);
                        }}
                      >
                        <Typography variant="caption">최고가 :</Typography>
                        <Typography variant="caption" color="error">
                          {numberFormat(data.highest_price)}

  </Grid>
  <Grid item>

  </Grid>
</Grid> */}
    
    </>
  );
};

export default TestContainer;

const reload = keyframes`
0%{
opacity:0;
transform:translateY(-15px);
}
2%{
opacity:1;
transform:translateY(0px);
}98%{
  opacity:1;
  transform:translateY(0px);
}100%{
  opacity:0;
  transform:translateY(15px);
}
`



const AnimationDiv = styled.div`
  animation :${reload};
  animation-duration:30s;
  animation-iteration-count: infinite;
`

const NumberSpan = styled.span`
  font-weight: bold;
  font-size: 1rem;
  margin: 10px 10px;
  color: red;
`;

const StyledBidRank = styled.div`
  padding: 5%;
  box-sizing: border-box;
  position: absolute;
  height: 300px;
  width: 100%;
  top: 1px;
  right: 1px;
`;

const StyledClickRank = styled.div`
  padding: 5%;
  box-sizing: border-box;
  position: absolute;
  height: 300px;
  width: 100%;
  top: 1px;
  right: 1px;
`;

const BidImg = styled.img`
  position: absolute;
  width: auto;
  height: 250px;
  border-radius: 10px;
  top: -20px;
  left: 20px;
  box-shadow: 1px 1px 4px #a3aae1;
`;

const ClickImg = styled.img`
  position: absolute;
  width: autopx;
  height: 250px;
  border-radius: 10px;
  right: 20px;
  top: -20px;
  box-shadow: 1px 1px 4px #a3aae1;
`;

const StyledDiv = styled.div`
  position: relative;
  box-shadow: 1px 1px 10px #a3aae1;
  width: 80%;
  height: 300px;
  border-radius: 10px;
`;
