const fs = require('fs').promises;

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

const getFirstDigitOfLine = (line) => {
  const firstDigit = line.match(/\d/);
  return firstDigit ? parseInt(firstDigit[0]) : null;
};

const getLastDigitOfLine = (line) => {
  const lastDigit = line.match(/\d(?=\D*$)/);
  return lastDigit ? parseInt(lastDigit[0]) : null;
};

const main = async (inPath, outPath) => {
  const data = await readInput(inPath);

  const lines = data.split('\n');
  let sum = 0;

  lines.forEach((line) => {
    const firstDigit = getFirstDigitOfLine(line);
    const lastDigit = getLastDigitOfLine(line);

    sum += firstDigit * 10 + lastDigit;
  });

  const isWritten = await writeOutput(outPath, sum.toString());
  isWritten ? console.log('Success!') : console.log('Failure!');
};

module.exports = {
  readInput,
  writeOutput,
  getFirstDigitOfLine,
  getLastDigitOfLine,
  main,
};
