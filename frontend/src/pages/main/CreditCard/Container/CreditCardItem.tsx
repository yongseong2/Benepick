import React from 'react';

import { StyleSheet, TouchableHighlight, View, Image } from 'react-native';

import WhiteBox from '@common/components/WhiteBox';
import BText from '@common/components/BText';
import { Spacing } from '@common/components/Spacing';
import CardProgress from './progress/CardProgress';
import CategoryText from '@common/components/CategoryText';
import { CreditCardItemProps } from '@interfaces/creditCard';

function CreditCardItem({
  cardName,
  cardType,
  image,
  benefitAmount,
  usedAmount,
  nextSection,
  ...rest
}: CreditCardItemProps) {
  return (
    <WhiteBox>
      <TouchableHighlight underlayColor="transparent" {...rest}>
        <View>
          <View style={styles.cardTitle}>
            <Image style={styles.image} source={image} />
            <Spacing dir="row" />
            <View>
              <BText type="h3">{cardName}</BText>
              <BText>{cardType}</BText>
            </View>
          </View>
          <Spacing />
          <View style={styles.progress}>
            <CardProgress current={3} page={3} size={25} />
          </View>
          <Spacing />
          <CategoryText category="실적/사용금액" value={`${benefitAmount}원/${usedAmount}원`} />
          <CategoryText category="다음 구간까지" value={`${nextSection}원 남음`} />
        </View>
      </TouchableHighlight>
    </WhiteBox>
  );
}

const styles = StyleSheet.create({
  cardTitle: {
    flexDirection: 'row',
  },
  image: {
    maxWidth: '15%',
    aspectRatio: 1 / 1.58,
  },
  progress: {
    alignItems: 'center',
  },
});

export default CreditCardItem;