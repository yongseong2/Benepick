import React from 'react';
import { TouchableOpacity, StyleSheet } from 'react-native';
import { SubmitButtonProps } from '@common/interfaces/components';
import colors from '@common/design/colors';
import BText from './BText';

function SubmitButton({ title, ...rest }: SubmitButtonProps) {
  return (
    <TouchableOpacity style={styles.button} {...rest}>
      <BText type="p" color={colors.white}>
        {title}
      </BText>
    </TouchableOpacity>
  );
}
const styles = StyleSheet.create({
  button: {
    backgroundColor: colors.main,
    padding: 12,
    borderRadius: 12,
    alignItems: 'center',
    margin: 5,
  },
});

export default SubmitButton;
