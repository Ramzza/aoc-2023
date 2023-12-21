const fs = require('fs').promises;

const UseCase = {
  FIRST: 'firstDigit',
  LAST: 'lastDigit',
};

const readInput = async (path) => {
  try {
    const data = await fs.readFile(path, 'utf8');
    return data;
  } catch (err) {
    console.log(err);
    return null;
  }
};

const writeOutput = async (path, data) => {
  try {
    await fs.writeFile(path, data);
    return true;
  } catch (err) {
    console.log(err);
    return false;
  }
};

const convertTextToNumber = (text) => {
  const textToNumber = {
    one: 1,
    two: 2,
    three: 3,
    four: 4,
    five: 5,
    six: 6,
    seven: 7,
    eight: 8,
    nine: 9,
  };
  return textToNumber[text] ?? null;
};

const reverseString = (str) => {
  return str.split('').reverse().join('');
};

const getRegexForUseCase = (useCase, isTxtOn) => {
  switch (useCase) {
    case UseCase.FIRST:
      return isTxtOn ? /\d|one|two|three|four|five|six|seven|eight|nine/ : /\d/;
    case UseCase.LAST:
      if (isTxtOn) {
        let sequences = [
          'one',
          'two',
          'three',
          'four',
          'five',
          'six',
          'seven',
          'eight',
          'nine',
        ];
        sequences = sequences.map((str) => reverseString(str));
        sequences.push('\\d');
        const regex = new RegExp(`${sequences.join('|')}`, 'g');
        return regex;
      } else {
        return /\d(?=\D*$)/;
      }
    default:
      return null;
  }
};

const getDigitFromLine = (line, useCase, isTextSearchOn) => {
  const regex = getRegexForUseCase(useCase, isTextSearchOn);
  line =
    isTextSearchOn && useCase === UseCase.LAST ? reverseString(line) : line;
  const findings = line.match(regex);

  if (findings) {
    findings[0] =
      isTextSearchOn && useCase === UseCase.LAST
        ? reverseString(findings[0])
        : findings[0];
    const digit = parseInt(findings[0]);
    return isNaN(digit) ? convertTextToNumber(findings[0]) : digit;
  } else {
    return null;
  }
};

const main = async (inPath, outPath, isTextSearchOn) => {
  const data = await readInput(inPath);

  const lines = data.split('\n');
  let sum = 0;

  lines.forEach((line) => {
    const firstDigit = getDigitFromLine(line, UseCase.FIRST, isTextSearchOn);
    const lastDigit = getDigitFromLine(line, UseCase.LAST, isTextSearchOn);

    sum += firstDigit * 10 + lastDigit;
  });

  const isWritten = await writeOutput(outPath, sum.toString());
  isWritten ? console.log('Success!') : console.log('Failure!');
};

module.exports = {
  readInput,
  writeOutput,
  convertTextToNumber,
  getDigitFromLine,
  getRegexForUseCase,
  UseCase,
  main,
};
